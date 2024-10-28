package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 구매서 상세 조회용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDetailDto {
    private Long id;
    private LocalDate date;
    private Long clientId;
    private String clientCode;
    private String clientName;
    private Long managerId;
    private String managerCode;
    private String managerName;
    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private Long vatId;
    private String vatCode;
    private String vatName;
    private String journalEntryCode;
    private String journalEntryName;
    private String electronicTaxInvoiceStatus;
    private Long currencyId;
    private String currency;
    private BigDecimal exchangeRate;
    private String remarks;
    private String status;
    private List<PurchaseItemDetailDto> purchaseDetails;

    /**
     * 구매서 상세 항목에 대한 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseItemDetailDto {
        private Long productId;
        private String productName;
        private String productCode;
        private String standard;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal supplyPrice;
        private BigDecimal localAmount;
        private BigDecimal vat;
        private String remarks;
    }


}
