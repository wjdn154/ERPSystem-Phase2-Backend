package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public interface UnresolvedVoucherEntryService {

    List<UnresolvedVoucher> unresolvedVoucherEntry(Long companyId, List<UnresolvedVoucherEntryDTO> dtoList);

//    UnresolvedVoucher createUnresolvedVoucher(UnresolvedVoucherEntryDTO dto, String voucherNum, LocalDateTime nowTime);

    UnresolvedVoucher createUnresolvedVoucher(UnresolvedVoucherEntryDTO dto, String voucherNum, LocalDateTime nowTime,
                                              Long companyId);

    boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(UnresolvedVoucherEntryDTO dto);

//    String CreateUnresolvedVoucherNumber(LocalDate voucherDate, VoucherKind voucherKind);

    String CreateUnresolvedVoucherNumber(LocalDate voucherDate, VoucherKind voucherKind, Long companyId);

    UnresolvedVoucherEntryDTO autoCreateUnresolvedVoucherDto(UnresolvedVoucherEntryDTO dto) throws CloneNotSupportedException;

//    List<UnresolvedVoucher> unresolvedVoucherAllSearch(LocalDate date);

    List<UnresolvedVoucher> unresolvedVoucherAllSearch(Long companyId, LocalDate date);

    List<Long> deleteUnresolvedVoucher(Long companyId, UnresolvedVoucherDeleteDTO dto);

    BigDecimal calculateTotalAmount(List<UnresolvedVoucher> vouchers, Function<UnresolvedVoucher, BigDecimal> amount);

    BigDecimal totalDebit(List<UnresolvedVoucher> vouchers);

    BigDecimal totalCredit(List<UnresolvedVoucher> vouchers);

    List<UnresolvedVoucher> voucherApprovalProcessing(Long companyId, UnresolvedVoucherApprovalDTO dto);
}
