package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface JournalService {

    List<UnresolvedVoucher> journalSearch(LocalDate StartDate, LocalDate EndDate, Long companyId);

    List<BigDecimal> journalTotalAmount(LocalDate StartDate, LocalDate EndDate, Long companyId);

    BigDecimal journalTotalCount(LocalDate StartDate, LocalDate EndDate, Long companyId);
}
