package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Currency;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 발주요청 테이블
 * 발주요청서에 대한 정보가 있는 테이블
 * 각 부서 담당자는 구매 관리 부서에 발주를 요청하기 위해 발주 요청을 한다.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주 요청과 발주 계획 간의 중간 엔티티와의 일대다 관계
    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchasePlanRequest> purchasePlanRequests = new ArrayList<>();

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

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 비고
    @Column
    private String remarks;
}
