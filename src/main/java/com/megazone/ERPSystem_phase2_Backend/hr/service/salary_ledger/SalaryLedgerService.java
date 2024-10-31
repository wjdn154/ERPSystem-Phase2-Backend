package com.megazone.ERPSystem_phase2_Backend.hr.service.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.*;

import java.util.List;

public interface SalaryLedgerService {
    SalaryLedgerDTO showSalaryLedger(SalaryLedgerSearchDTO dto);

    FinalizedDTO salaryLedgerDeadLineOn(Long salaryLedgerId);

    FinalizedDTO salaryLedgerDeadLineOff(Long salaryLedgerId);

    SalaryLedgerDTO salaryLedgerCalculator(Long salaryLedgerId);

    SalaryLedgerDTO updateSalaryLedger(SalaryLedgerDTO dto);

    List<PaymentStatusManagementShowDTO> showPaymentStatusManagement(PaymentStatusManagementSearchDTO dto);
}
