package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.*;

/**
 * 발주요청 상세 테이블
 * 발주요청서에 포함된 품목과 관련된 정보 관리
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDetail {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주 요청과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest;

    // 품목과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 공급가액 (수량 * 단가)
    @Column(nullable = false)
    private Double supplyPrice;

    // 원화금액 (통화가 외자일때만 사용)
    @Column(nullable = true)
    private Double localAmount;

    // 부가세
    @Column
    private Double vat;

    // 비고
    @Column
    private String remarks;
}
