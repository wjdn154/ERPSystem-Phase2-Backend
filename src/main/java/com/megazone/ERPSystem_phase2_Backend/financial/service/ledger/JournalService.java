package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface JournalService {
    List<ResolvedVoucher> journalSearch(LocalDate StartDate, LocalDate EndDate);

    List<BigDecimal> journalTotalAmount(LocalDate StartDate, LocalDate EndDate);

    BigDecimal journalTotalCount(LocalDate StartDate, LocalDate EndDate);
}
