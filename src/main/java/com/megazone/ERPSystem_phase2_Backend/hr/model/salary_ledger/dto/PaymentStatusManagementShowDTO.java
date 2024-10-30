package com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentStatusManagementShowDTO {
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
    private BigDecimal nonTaxableAmount; // 비과세 금액
    private BigDecimal taxableAmount; // 과세 금액
    private boolean finalized; // 마감구분
    private List<SalaryLedgerAllowanceShowDTO> allowances = new ArrayList<>();

    private BigDecimal sumTotalSalaryAmount; // 지급총액 합계
    private BigDecimal sumTotalDeductionAmount; // 공제합계
    private BigDecimal sumTotalNetPayment; // 차인지급액 합계
    private int count; // 인원수
}
