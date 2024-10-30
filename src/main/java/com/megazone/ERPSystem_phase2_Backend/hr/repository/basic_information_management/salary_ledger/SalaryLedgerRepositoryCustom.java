package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.PaymentStatusManagementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.PaymentStatusManagementShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;

import java.util.List;

public interface SalaryLedgerRepositoryCustom {
    SalaryLedgerDTO findLedger(SalaryLedgerSearchDTO dto);
    List<PaymentStatusManagementShowDTO> showPaymentStatusManagement(PaymentStatusManagementSearchDTO dto);
}
