package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Salary;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO{

    private String firstname;         // 직원 성
    private String lastname;          // 직원 이름
    private BigDecimal baseSalary;   // 기본 급여
    private BigDecimal grossPay;  // 총 급여
    private BigDecimal netPay;    // 순 급여
    private LocalDate salaryDate;    // 급여 지급일

    private List<AllowanceDTO> allowances;   // 수당 리스트
    private List<DeductionDTO> deductions;   // 공제 리스트

    public static SalaryDTO convertToDTO(Salary salary) {
        // 보너스 리스트 변환
        List<AllowanceDTO> allowanceDTOs = salary.getAllowances().stream()
                .map(allowance -> {
                    AllowanceDTO allowanceDTO = new AllowanceDTO();
                    allowanceDTO.setId(allowance.getId());
                    allowanceDTO.setName(allowance.getName());
                    allowanceDTO.setAmount(allowance.getAmount());
                    allowanceDTO.setType(String.valueOf(allowance.getAllowanceType()));
                    return allowanceDTO;
                }).collect(Collectors.toList());

        // 공제 리스트 변환
        List<DeductionDTO> deductionDTOs = salary.getDeductions().stream()
                .map(deduction -> {
                    DeductionDTO deductionDTO = new DeductionDTO();
                    deductionDTO.setId(deduction.getId());
                    deductionDTO.setAmount(deduction.getAmount());
                    deductionDTO.setType(String.valueOf(deduction.getDeductionType()));
                    return deductionDTO;
                }).collect(Collectors.toList());

        return new SalaryDTO(
                salary.getEmployee().getFirstName(),
                salary.getEmployee().getLastName(),
                salary.getBaseSalary(),
                salary.getGrossPay(),
                salary.getNetPay(),
                salary.getSalaryDate(),
                allowanceDTOs,
                deductionDTOs

        );

    }

}