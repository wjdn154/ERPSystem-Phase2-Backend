package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_adjustment_detail")
public class InventoryAdjustmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // InventoryAdjustment 테이블과의 다대일 관계 (한 번의 조정에 여러 품목이 조정될 수 있음)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_adjustment_id", nullable = false)
    private InventoryAdjustment inventoryAdjustment;  // 재고 조정과 연관

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 조정된 품목

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "standard")  // 추가: 규격 필드
    private String standard;

    @Column(name = "unit")  // 추가: 단위 필드
    private String unit;

    @Column(name = "book_quantity", nullable = false)
    private Long bookQuantity;  // 장부상의 수량

    @Column(name = "adjustment_quantity", nullable = false)
    private Long adjustmentQuantity;  // 조정된 수량

    @Column(name = "comment", nullable = true)
    private String comment;  // 비고

}
