package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 발주서 상세 테이블
 * 발주서에 포함된 품목과 관련된 정보 관리
 */
@Entity
@Data
@ToString(exclude = "purchaseOrder")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주서와의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    // 품목과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 수량
    @Column
    private Integer quantity;

    // 공급가액 (수량 * 단가)
    @Column
    private BigDecimal supplyPrice;

    // 원화금액 (통화가 외자일때만 사용)
    @Column(nullable = true)
    private BigDecimal localAmount;

    // 부가세
    @Column
    private BigDecimal vat;

    // 비고
    @Column
    private String remarks;
}
