package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 구매서 상세 테이블
 * 구매서 포함된 품목과 관련된 정보 관리
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매서와의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    // 품목과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 공급가액 (수량 * 단가)
    @Column(nullable = false)
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
