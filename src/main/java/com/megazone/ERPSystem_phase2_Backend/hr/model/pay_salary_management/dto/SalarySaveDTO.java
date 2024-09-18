package com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.AllowanceAmount;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_salary_management.DeductionAmount;
import com.megazone.ERPSystem_phase2_Backend.hr.model.pay_social_insurance_management.SocialInsurance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalarySaveDTO {
    private Long employeeId;
    private BigDecimal baseSalary;
    private List<DeductionAmountDTO> deductionAmount;
    private List<AllowanceAmountDTO> allowanceAmount;
    private SocialInsurance socialInsurance;
    private BigDecimal netSalary;
    private LocalDate month;
}
