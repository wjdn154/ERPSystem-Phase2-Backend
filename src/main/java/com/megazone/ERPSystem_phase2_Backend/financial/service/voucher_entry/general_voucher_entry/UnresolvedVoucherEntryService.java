package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface UnresolvedVoucherEntryService {

    List<UnresolvedVoucher> unresolvedVoucherEntry(List<UnresolvedVoucherEntryDTO> dtoList);

    UnresolvedVoucher createUnresolvedVoucher(UnresolvedVoucherEntryDTO dto, Long voucherNum, LocalDateTime nowTime);

    boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(UnresolvedVoucherEntryDTO dto);

    Long CreateUnresolvedVoucherNumber(LocalDate voucherDate, VoucherKind voucherKind);

    UnresolvedVoucherEntryDTO autoCreateUnresolvedVoucherDto(UnresolvedVoucherEntryDTO dto) throws CloneNotSupportedException;

    List<UnresolvedVoucher> unresolvedVoucherAllSearch(LocalDate date);

    List<Long> deleteUnresolvedVoucher(UnresolvedVoucherDeleteDTO dto);

    BigDecimal calculateTotalAmount(List<UnresolvedVoucher> vouchers, Function<UnresolvedVoucher, BigDecimal> amount);

    BigDecimal totalDebit(List<UnresolvedVoucher> vouchers);

    BigDecimal totalCredit(List<UnresolvedVoucher> vouchers);

    List<UnresolvedVoucher> voucherApprovalProcessing(UnresolvedVoucherApprovalDTO dto);

    UnresolvedVoucherShowAllDTO unresolvedVoucherApprovalSearch(LocalDate date);
}
