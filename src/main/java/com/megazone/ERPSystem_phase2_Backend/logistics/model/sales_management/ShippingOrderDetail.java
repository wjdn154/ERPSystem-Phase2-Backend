package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 출하 지시서 상세 테이블
 * 출하 지시서에  포함된 품목과 관련된 정보 관리
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출하 지시서와의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_order_id", nullable = false)
    private ShippingOrder shippingOrder;

    // 품목과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 비고
    @Column
    private String remarks;
}
