package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ResolvedVoucherShowAllDTO {
    private LocalDate searchDate;
    private List<UnresolvedVoucherShowDTO> voucherDtoList;
    private BigDecimal cashAmount;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal diffCashAmount;

    public static UnresolvedVoucherShowAllDTO create(LocalDate searchDate, List<UnresolvedVoucherShowDTO> voucherDtoList, BigDecimal cashAmount,
                                                     BigDecimal totalDebit, BigDecimal totalCredit) {
        return new UnresolvedVoucherShowAllDTO(
                searchDate,
                voucherDtoList,
                cashAmount,
                totalDebit,
                totalCredit,
                totalDebit.subtract(totalCredit)
        );
    }
}
