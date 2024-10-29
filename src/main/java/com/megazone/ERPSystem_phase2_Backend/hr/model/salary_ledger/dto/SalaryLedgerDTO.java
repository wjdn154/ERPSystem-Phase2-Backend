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
    private BigDecimal nationalPensionAmount; // 국민연금 공제액
    private BigDecimal privateSchoolPensionAmount; // 사학연금 공제액
    private BigDecimal healthInsurancePensionAmount; // 건강보험 공제액
    private BigDecimal employmentInsuranceAmount; // 국민연금 공제액
    private BigDecimal longTermCareInsurancePensionAmount; // 장기요양보험 금액
    private BigDecimal incomeTaxAmount; // 소득세 공제액
    private BigDecimal localIncomeTaxPensionAmount; // 지방소득세 금액
    private BigDecimal totalSalaryAmount; // 지급총액
    private BigDecimal totalDeductionAmount; // 공제총액
    private BigDecimal netPayment; // 차인지급액
    private boolean finalized; // 마감구분
    private List<SalaryLedgerAllowanceShowDTO> allowances = new ArrayList<>();

    public static SalaryLedgerDTO create(SalaryLedger salaryLedger) {
        return new SalaryLedgerDTO(
                salaryLedger.getId(),
                salaryLedger.getNationalPensionAmount(),
                salaryLedger.getPrivateSchoolPensionAmount(),
                salaryLedger.getHealthInsurancePensionAmount(),
                salaryLedger.getEmploymentInsuranceAmount(),
                salaryLedger.getLongTermCareInsurancePensionAmount(),
                salaryLedger.getIncomeTaxPensionAmount(),
                salaryLedger.getLocalIncomeTaxPensionAmount(),
                salaryLedger.getTotalSalaryAmount(),
                salaryLedger.getTotalDeductionAmount(),
                salaryLedger.getNetPayment(),
                salaryLedger.isFinalized(),
                salaryLedger.getAllowance().stream().map((change) -> {
                    return SalaryLedgerAllowanceShowDTO.create(change.getName(),change.getAmount());
                }).toList());
    }
}
