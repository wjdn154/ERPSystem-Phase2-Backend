package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnresolvedVoucherShowAllDto {
    private LocalDate searchDate;
    private List<UnresolvedVoucherShowDto> voucherDtoList;
    private BigDecimal cashAmount;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal diffCashAmount;

    public static UnresolvedVoucherShowAllDto create(LocalDate searchDate, List<UnresolvedVoucherShowDto> voucherDtoList, BigDecimal cashAmount,
                                       BigDecimal totalDebit, BigDecimal totalCredit) {
        return new UnresolvedVoucherShowAllDto(
                searchDate,
                voucherDtoList,
                cashAmount,
                totalDebit,
                totalCredit,
                totalDebit.subtract(totalCredit)
        );
    }



}
