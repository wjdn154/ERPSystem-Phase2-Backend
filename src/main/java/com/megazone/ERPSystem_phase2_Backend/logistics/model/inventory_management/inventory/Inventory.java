package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
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
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "warehouse_location_id", nullable = false)
    private WarehouseLocation warehouseLocation;

    @Column(name = "inventory_number", nullable = false, unique = true)
    private Long inventoryNumber; // 재고번호

    @Column(name = "standard")
    private String standard;  // 재고의 기준 (예: 단위, 규격 등)

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public void updateQuantity(Long newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        this.quantity = newQuantity;
    }

    public void updateLocation(WarehouseLocation newLocation) {
        if (newLocation == null) {
            throw new IllegalArgumentException("위치는 null일 수 없습니다.");
        }
        this.warehouseLocation = newLocation;
    }
}
