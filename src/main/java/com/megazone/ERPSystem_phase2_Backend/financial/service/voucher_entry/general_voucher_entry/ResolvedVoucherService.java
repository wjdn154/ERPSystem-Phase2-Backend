package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface ResolvedVoucherService {
    void resolvedVoucherEntry(List<UnresolvedVoucher> unresolvedVoucherList);

    ResolvedVoucher createResolvedVoucher(UnresolvedVoucher unresolvedVoucher, LocalDateTime approvalTime);
    List<ResolvedVoucher> resolvedVoucherAllSearch(LocalDate date);
    BigDecimal calculateTotalAmount(LocalDate date, Function<ResolvedVoucher, BigDecimal> amount);
    BigDecimal totalDebit(LocalDate date);
    BigDecimal totalCredit(LocalDate date);
    List<Long> deleteResolvedVoucher(ResolvedVoucherDeleteDTO dto);
}
