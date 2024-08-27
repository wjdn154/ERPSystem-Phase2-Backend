package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherDeleteDTO {
    private LocalDate searchDate;
    private List<String> searchVoucherNumList;
    private Long managerId; // 담당자 ID
}
