package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedVoucherShowAllDTO {
    private LocalDate searchDate;
    private List<ResolvedVoucherShowDTO> voucherDtoList;
    private BigDecimal cashAmount;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal diffCashAmount;

    public static ResolvedVoucherShowAllDTO create(LocalDate searchDate, List<ResolvedVoucherShowDTO> voucherDtoList, BigDecimal cashAmount,
                                                     BigDecimal totalDebit, BigDecimal totalCredit) {
        return new ResolvedVoucherShowAllDTO(
                searchDate,
                voucherDtoList,
                cashAmount,
                totalDebit,
                totalCredit,
                totalDebit.subtract(totalCredit)
        );
    }
}
