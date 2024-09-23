package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Currency;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 발주서 테이블
 * 발주서에 대한 정보가 있는 테이블
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주 계획 외래 키
    @ManyToOne
    @JoinColumn(name = "purchase_plan_id")
    private PurchasePlan purchasePlan;

    // 발주서와 구매서 간의 일대일 관계
    @OneToOne(mappedBy = "purchaseOrder")
    private Purchase purchase;

    // 거래처_id - N : 1
    @Column(nullable = false)
    private Long clientId;

    // 사원_id - N : 1
    @Column(nullable = false)
    private Long employeeId;

    // 창고_id - 입고될 창고
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    // 통화_id
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

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
