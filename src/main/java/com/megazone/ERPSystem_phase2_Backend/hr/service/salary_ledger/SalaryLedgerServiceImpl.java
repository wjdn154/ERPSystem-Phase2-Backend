package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedgerDate;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration.PositionSalaryStepRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary.SalaryRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerDateRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger.SalaryLedgerRepository;
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


    /**
     * 사원 id 받아서 조회하고 해당월에 급여정보 등록한적없으면 새로생성
     * 결산되지않은 급여정보 있으면 불러오고
     * 결산하면 더이상 수정불가
     */

    @Override
    public List<SalaryLedgerDTO> showSalaryLedger(SalaryLedgerSearchDTO dto) {
        List<SalaryLedgerDTO> result = salaryLedgerRepository.findLedger(dto);

        if(result == null) {
            Salary salary = salaryRepository.findByEmployeeId(dto.getEmployeeId());
            SalaryLedgerDate salaryLedgerDate = salaryLedgerDateRepository.findById(dto.getSalaryLedgerDateId())
                    .orElseThrow(() ->new NoSuchElementException("해당하는 급여 지급정보가 없습니다."));
            Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                    () -> new NoSuchElementException("해당하는 사원 정보가 없습니다."));

            SalaryLedger newLedger = new SalaryLedger();
            newLedger.setSalaryLedgerDate(salaryLedgerDate);
                newLedger.setEmployee(employee);
                newLedger.setNationalPensionAmount(BigDecimal.ZERO);  // 초기값 설정
                newLedger.setPrivateSchoolPensionAmount(BigDecimal.ZERO); // 초기값 설정
                newLedger.setHealthInsurancePensionAmount(BigDecimal.ZERO); // 초기값 설정
                newLedger.setEmploymentInsuranceAmount(BigDecimal.ZERO); // 초기값 설정

            // 수당 리스트 변환 후 설정
            List<SalaryLedgerAllowanceDTO> allowanceDTOs = positionSalaryStepRepository
                    .getSalaryAllowance(employee.getId(), salary.getSalaryStep().getId());

            List<SalaryLedgerAllowance> allowances = allowanceDTOs.stream()
                    .map(dtoItem -> new SalaryLedgerAllowance(dtoItem.getName(), dtoItem.getAmount()))
                    .collect(Collectors.toList());

            newLedger.setAllowance(allowances);  // 변환된 수당 리스트 설정

            salaryLedgerRepository.save(newLedger);
            return null;
        }
        return result;
    }
}
