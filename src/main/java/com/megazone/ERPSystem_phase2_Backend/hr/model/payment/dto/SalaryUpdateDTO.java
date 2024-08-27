package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.Deduction;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.AllowanceType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.payment.enums.DeductionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryUpdateDTO {

    private DeductionType deductionType;  // 공제 리스트
    private BigDecimal deductionAmount;

    private AllowanceType allowanceType;  // 수당 리스트
    private BigDecimal allowanceAmount;

    private BigDecimal baseSalary;
}
