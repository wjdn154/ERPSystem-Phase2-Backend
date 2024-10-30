package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryLedgerShowAllDTO {
    private Long id;
    private List<SalaryLedgerAllowanceShowDTO> allowanceDTOS;
    private boolean finalized;
    private BigDecimal nationalPensionAmount; // 국민연금 금액
    private BigDecimal privateSchoolPensionAmount; // 사학연금 금액
    private BigDecimal healthInsurancePensionAmount; // 건강보험 금액
    private BigDecimal employmentInsuranceAmount; // 고용보험 금액
}
