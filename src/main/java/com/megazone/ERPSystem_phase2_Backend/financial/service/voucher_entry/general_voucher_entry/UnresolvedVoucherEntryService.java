package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.GeneralVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;

import java.time.LocalDate;
import java.util.List;

public interface UnresolvedVoucherEntryService {
    List<UnresolvedVoucher> unresolvedVoucherEntry(List<GeneralVoucherEntryDto> dtoList);
    UnresolvedVoucher createUnresolvedVoucher(GeneralVoucherEntryDto dto, String voucherNum);
    boolean depositAndWithdrawalUnresolvedVoucherTypeCheck(GeneralVoucherEntryDto dto);
    String CreateUnresolvedVoucherNumber(LocalDate voucherDate);
    GeneralVoucherEntryDto autoCreateUnresolvedVoucherDto(GeneralVoucherEntryDto dto) throws CloneNotSupportedException;
}
