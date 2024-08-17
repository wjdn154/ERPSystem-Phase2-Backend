package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherDeleteDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface UnresolvedVoucherEntryService {

    List<UnresolvedVoucher> unresolvedVoucherEntry(List<UnresolvedVoucherEntryDto> dtoList);

    UnresolvedVoucher createUnresolvedVoucher(UnresolvedVoucherEntryDto dto, String voucherNum, LocalDateTime nowTime);

    boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(UnresolvedVoucherEntryDto dto);

    String CreateUnresolvedVoucherNumber(LocalDate voucherDate);

    UnresolvedVoucherEntryDto autoCreateUnresolvedVoucherDto(UnresolvedVoucherEntryDto dto) throws CloneNotSupportedException;

    List<UnresolvedVoucher> unresolvedVoucherAllSearch(LocalDate date);

    List<Long> deleteUnresolvedVoucher(UnresolvedVoucherDeleteDto dto);

    BigDecimal calculateTotalAmount(LocalDate date, Function<UnresolvedVoucher, BigDecimal> amount);

    BigDecimal totalDebit(LocalDate date);

    BigDecimal totalCredit(LocalDate date);
}
