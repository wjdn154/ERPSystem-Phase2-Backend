package com.megazone.ERPSystem_phase2_Backend.hr.service.payment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.Salary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto.SalaryListDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management.AllowanceAmountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management.DeductionAmountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_salary_management.SalaryRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_social_insurance_management.SocialInsuranceAmountRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.pay_social_insurance_management.SocialInsuranceRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final AllowanceAmountRepository allowanceAmountRepository;
    private final DeductionAmountRepository deductionAmountRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SocialInsuranceRateRepository socialInsuranceRateRepository;

    @Autowired
    private SocialInsuranceAmountRepository socialInsuranceAmountRepository;


    // 월급 리스트
    @Override
    public List<SalaryListDTO> findAllEmployeesAndSalary() {
        List<Salary> salaries = salaryRepository.findAll();
        List<SalaryListDTO> dtos = new ArrayList<>();
        for (Salary salary : salaries) {
            dtos.add(SalaryListDTO.create(salary));
        }
        return dtos;
    }

    // 직원 ID로 월급 데이터를 조회
    public Optional<SalaryDetailDTO> findSalaryByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId)
                .map(salary -> new SalaryDetailDTO(
                        salary.getId(),
                        salary.getEmployee().getId(),
                        salary.getEmployee().getEmployeeNumber(),
                        salary.getEmployee().getFirstName(),
                        salary.getEmployee().getLastName(),
                        salary.getBaseSalary(),
                        salary.getAllowanceAmounts(),
                        salary.getDeductionAmounts(),
                        salary.getSocialInsuranceAmount(),
                        salary.getNetPay(),
                        salary.getMonth()));
    }


    // 월급 리스트에서 월급을 생성 및 수정
    public List<SalaryListDTO> createOrUpdateSalary(List<SalaryListDTO> salaryListDTOs) {
        for (SalaryListDTO dto : salaryListDTOs) {
            // 직원Id로 월급 데이터가 있는지 조회
            Optional<Salary> salaryOpt = salaryRepository.findByEmployeeId(dto.getEmployeeId());

            if (salaryOpt.isPresent()) {
                // 기존 월급이 존재할 경우, 업데이트
                Salary saveSalary = salaryOpt.get();
                saveSalary.setBaseSalary(dto.getBaseSalary());
                saveSalary.setAllowanceAmounts(dto.getAllowances());
                saveSalary.setDeductionAmounts(dto.getDeductions());
                saveSalary.calculateNoneTaxSalary();
                salaryRepository.save(saveSalary);
            } else {
                // 월급이 존재하지 않으면 새로 생성
                Salary newSalary = new Salary();
                newSalary.setBaseSalary(dto.getBaseSalary());
                newSalary.setAllowanceAmounts(dto.getAllowances());
                newSalary.setDeductionAmounts(dto.getDeductions());
                newSalary.calculateNoneTaxSalary();
                salaryRepository.save(newSalary);
            }
        }
        return salaryListDTOs;
    }

    @Override
    public void deleteSalary(Long id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new RuntimeException("월급을 찾을수 없습니다."));
        salaryRepository.delete(salary);
    }
}