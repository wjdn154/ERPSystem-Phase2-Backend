package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
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
    private BigDecimal nationalPensionAmount;
    private BigDecimal privateSchoolPensionAmount;
    private BigDecimal healthInsurancePensionAmount;
    private BigDecimal employmentInsuranceAmount;
    private List<SalaryLedgerAllowanceDTO> allowances = new ArrayList<>();

}
