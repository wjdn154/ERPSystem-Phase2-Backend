package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.LongTermCareInsuranceCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerDate;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerDateRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.EmploymentInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.HealthInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.LongTermCareInsurancePensionService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.NationalPensionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SalaryLedgerServiceImpl implements SalaryLedgerService {

    private final SalaryLedgerRepository salaryLedgerRepository;
    private final SalaryLedgerDateRepository salaryLedgerDateRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionSalaryStepRepository positionSalaryStepRepository;
    private final SalaryRepository salaryRepository;
    private final HealthInsurancePensionService healthInsurancePensionService;
    private final NationalPensionService nationalPensionService;
    private final LongTermCareInsurancePensionService longTermCareInsurancePensionService;
    private final EmploymentInsurancePensionService employmentInsurancePensionService;
    private final LongTermCareInsurancePensionRepository longTermCareInsurancePensionRepository;


    /**
     * 사원 id 받아서 조회하고 해당월에 급여정보 등록한적없으면 새로생성
     * 결산되지않은 급여정보 있으면 불러오고
     * 결산하면 더이상 수정불가
     */

    @Override
    public SalaryLedgerDTO showSalaryLedger(SalaryLedgerSearchDTO dto) {
        SalaryLedgerDTO result = salaryLedgerRepository.findLedger(dto);

        if(result == null) {
            Salary salary = salaryRepository.findByEmployeeId(dto.getEmployeeId());

            SalaryLedgerDate salaryLedgerDate = salaryLedgerDateRepository.findById(dto.getSalaryLedgerDateId())
                    .orElseThrow(() ->new NoSuchElementException("해당하는 급여 지급정보가 없습니다."));

            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                    () -> new NoSuchElementException("해당하는 사원 정보가 없습니다."));

            InsurancePensionCalculatorDTO calculatorDTO = new InsurancePensionCalculatorDTO(salary.getSalaryStep().getId()
            ,employee.getPosition().getId());

            BigDecimal HealthInsurancePensionAmount = healthInsurancePensionService.calculator(calculatorDTO);
            BigDecimal NationalPensionAmount = nationalPensionService.calculator(calculatorDTO);
            BigDecimal EmploymentInsurancePensionAmount = employmentInsurancePensionService.calculator(calculatorDTO);

            LongTermCareInsurancePension longTermCareInsurancePension = longTermCareInsurancePensionRepository.findByCode(salary.getLongTermCareInsurancePensionCode());

            LongTermCareInsuranceCalculatorDTO longTermDto = new LongTermCareInsuranceCalculatorDTO(HealthInsurancePensionAmount,
                    longTermCareInsurancePension.getId());

            BigDecimal LongTermCareInsurancePensionAmount = longTermCareInsurancePensionService.calculator(longTermDto);


            SalaryLedger newLedger = new SalaryLedger();
            newLedger.setSalaryLedgerDate(salaryLedgerDate);
                newLedger.setEmployee(employee);
                newLedger.setNationalPensionAmount(NationalPensionAmount);  // 초기값 설정
                newLedger.setPrivateSchoolPensionAmount(LongTermCareInsurancePensionAmount); // 초기값 설정
                newLedger.setHealthInsurancePensionAmount(HealthInsurancePensionAmount); // 초기값 설정
                newLedger.setEmploymentInsuranceAmount(EmploymentInsurancePensionAmount); // 초기값 설정

            // 수당 리스트 변환 후 설정
            List<SalaryLedgerAllowanceDTO> allowanceDTOs = positionSalaryStepRepository
                    .getSalaryAllowance(employee.getId(), salary.getSalaryStep().getId());

            List<SalaryLedgerAllowance> allowances = allowanceDTOs.stream()
                    .map(dtoItem -> new SalaryLedgerAllowance(dtoItem.getName(), dtoItem.getAmount()))
                    .collect(Collectors.toList());

            newLedger.setAllowance(allowances);  // 변환된 수당 리스트 설정

            SalaryLedger salaryLedger = salaryLedgerRepository.save(newLedger);

            return new SalaryLedgerDTO(
                    salaryLedger.getId(),
                    salaryLedger.getNationalPensionAmount(),
                    salaryLedger.getPrivateSchoolPensionAmount(),
                    salaryLedger.getHealthInsurancePensionAmount(),
                    salaryLedger.getEmploymentInsuranceAmount(),
                    allowanceDTOs);

        }
        return result;
    }
}
