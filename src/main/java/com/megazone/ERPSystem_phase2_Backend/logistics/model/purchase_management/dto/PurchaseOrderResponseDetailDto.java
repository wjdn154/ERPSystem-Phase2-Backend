package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 발주서 상세 조회용 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponseDetailDto {

    // 발주서 ID
    private Long id;

    // 발주서 작성 일자
    private LocalDate date;

    // 납기 일자
    private LocalDate deliveryDate;

    private Long clientId;

    private String clientName;

    private Long managerId;

    // 담당자 코드
    private String managerCode;

    // 담당자 이름
    private String managerName;

    private Long warehouseId;

    // 입고 창고 코드
    private String warehouseCode;

    // 입고 창고 이름
    private String warehouseName;

    private Long vatId;
    private String vatCode;
    private String vatName;
    private String journalEntryCode;
    private String journalEntryName;
    private String electronicTaxInvoiceStatus;

    // 부가세 적용 여부
    private Boolean vatType;

    private Long currencyId;

    // 통화 종류
    private String currency;

    // 환율
    private BigDecimal exchangeRate;

    // 비고
    private String remarks;

    // 진행 상태
    private String status;

    // 발주 요청 상세 목록
    private List<PurchaseOrderItemDetailDto> purchaseOrderDetails;

    /**
     * 발주서 상세 항목에 대한 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseOrderItemDetailDto {
        private Long detailId;

        private Long productId;

        // 품목명
        private String productName;

        // 품목 코드
        private String productCode;

        // 품목 수량
        private Integer quantity;

        // 품목 단가(입고단가)
        private BigDecimal price;

        // 품목 공급가액
        private BigDecimal supplyPrice;

        // 품목 부가세
        private BigDecimal vat;

        // 비고
        private String remarks;

        // 거래처 정보 (품목에서 조회)
        private ClientDetailDto client;

        /**
         * 거래처 정보 DTO
         */
        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ClientDetailDto {
            // 거래처 ID
            private Long clientId;

            // 거래처 코드
            private String clientCode;

            // 거래처 이름
            private String clientName;
        }
    }
}
