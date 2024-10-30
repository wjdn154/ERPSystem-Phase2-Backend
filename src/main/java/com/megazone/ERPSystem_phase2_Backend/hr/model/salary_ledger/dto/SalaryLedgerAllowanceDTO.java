package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.Allowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.AllowanceType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.enums.TaxType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.Salary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerAllowanceDTO {
    private String code; // 수당코드
    private String name; // 수당이름
    private TaxType taxType; // 과세 여부 (과세, 비과세)
    private BigDecimal limitAmount; // 한도 금액 (비과세일 경우 해당)
    private BigDecimal allowanceAmount; // 수당금액
}
