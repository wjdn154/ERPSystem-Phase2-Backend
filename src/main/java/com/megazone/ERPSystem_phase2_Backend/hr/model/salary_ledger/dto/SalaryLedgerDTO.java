package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerDTO {
    private Long ledgerId;
    private BigDecimal nationalPensionAmount; // 국민연금 공제액
    private BigDecimal privateSchoolPensionAmount; //
    private BigDecimal healthInsurancePensionAmount; // 건강보험 공제액
    private BigDecimal employmentInsuranceAmount; // 국민연금 공제액
    private BigDecimal longTermCareInsurancePensionAmount; // 장기요양보험 금액
    private BigDecimal incomeTaxAmount; // 소득세 공제액
    private BigDecimal localIncomeTaxPensionAmount; // 지방소득세 금액
    private List<SalaryLedgerAllowanceDTO> allowances = new ArrayList<>();

}
