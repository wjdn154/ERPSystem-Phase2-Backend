package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 발주계획 테이블
 * 발주계획에 대한 정보가 있는 테이블
 * 발주요청에 따라 발주계획을 등록할 수 있어야 한다.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePlan {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주 요청_id 참조
    @Column
    private Long purchaseRequestId;

    // 거래처_id - N : 1
    @Column(nullable = false)
    private Long clientId;

    // 사원_id - N : 1
    @Column(nullable = false)
    private Long employeeId;

    // 창고_id - 입고될 창고
    @Column(nullable = false)
    private Long warehouseId;

    // 통화_id - N : 1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "currency_id", nullable = false)
    private Long currencyId;

    // 품목_id
    @Column
    private Long productId;

    // 수량
    @Column(nullable = false )
    private Integer quantity;

    // 공급가액 - 수량 * 단가
    @Column(nullable = false)
    private Double supplyPrice;

    // 부가세율 적용 or 미적용
    @Column(nullable = false)
    private Boolean vatType;

    // 부가세 - 공급가액의 10%
    @Column
    private Double vat;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;
}
