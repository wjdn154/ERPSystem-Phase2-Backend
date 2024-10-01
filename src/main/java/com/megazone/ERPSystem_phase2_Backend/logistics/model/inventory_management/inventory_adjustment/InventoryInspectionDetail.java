package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
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
@Table(name = "inventory_inspection_detail")
public class InventoryInspectionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_inspection_id", nullable = false)
    private InventoryInspection inventoryInspection;  // 실사와 연관

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 실사된 품목

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_location_id", nullable = false)
    private WarehouseLocation warehouseLocation;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "standard")  // 추가: 규격 필드
    private String standard;

    @Column(name = "unit")  // 추가: 단위 필드
    private String unit;

    @Column(name = "book_quantity")
    private Long bookQuantity;  // 장부상의 수량

    @Column(name = "actual_quantity", nullable = false)
    private Long actualQuantity;  // 실사된 실제 수량

    @Column(name = "difference_quantity")
    private Long differenceQuantity;  // 장부 수량과의 차이

    @Column(name = "comment")
    private String comment;  // 추가 정보 또는 비고

}
