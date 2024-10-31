package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.QInventory.inventory;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct.product;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.QWarehouseLocation.warehouseLocation;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<InventoryResponseDTO> findAllInventories() {
        return queryFactory.select(Projections.constructor(InventoryResponseDTO.class,
                        inventory.id,
                        inventory.inventoryNumber,
                        product.id,
                        product.code,
                        product.name,
                        inventory.standard,
                        inventory.product.unit,
                        inventory.quantity,
                        warehouse.name,
                        warehouseLocation.id,
                        warehouseLocation.locationName))
                .from(inventory)
                .join(inventory.product, product) // Product와 조인
                .join(inventory.warehouse, warehouse) // Warehouse와 조인
                .join(inventory.warehouseLocation, warehouseLocation) // WarehouseLocation과 조인
                .fetch();
    }

    @Override
    public List<InventoryResponseDTO> findInventoriesByLocationId(Long locationId) {
        return queryFactory.select(Projections.constructor(InventoryResponseDTO.class,
                        inventory.id,  // 재고 ID
                        inventory.inventoryNumber,  // 재고 번호
                        product.code,  // 품목 코드
                        product.name,  // 품목명
                        inventory.standard,  // 규격
                        inventory.quantity,  // 재고 수량
                        inventory.warehouse.name,  // 창고 이름
                        warehouseLocation.locationName))  // 위치 이름
                .from(inventory)
                .join(inventory.product, product)  // Product와 조인
                .join(inventory.warehouseLocation, warehouseLocation)  // WarehouseLocation과 조인
                .where(warehouseLocation.id.eq(warehouseLocation.id))  // 특정 위치 ID로 필터링
                .fetch();  // 결과 리스트 반환
    }

    @Override
    public boolean existsByInventoryNumber(Long inventoryNumber) {
        return queryFactory.selectFrom(inventory)
                .where(inventory.inventoryNumber.eq(inventoryNumber))
                .fetchFirst() != null;
    }

    @Override
    public List<InventoryResponseDTO> findInventoriesByWarehouseId(Long warehouseId) {
        return queryFactory.select(Projections.constructor(InventoryResponseDTO.class,
                        inventory.id,
                        inventory.inventoryNumber,
                        product.id,
                        product.code,
                        product.name,
                        inventory.product.standard,
                        inventory.product.unit,
                        inventory.quantity,
                        warehouse.name,
                        warehouseLocation.id,
                        warehouseLocation.locationName))
                .from(inventory)
                .join(inventory.product, product)
                .join(inventory.warehouse, warehouse)
                .join(inventory.warehouseLocation, warehouseLocation)
                .where(warehouse.id.eq(warehouseId))
                .fetch();
    }

    @Override
    public Long findMaxInventoryNumber() {
        Long maxInventoryNumber = queryFactory
                .select(inventory.inventoryNumber.max())
                .from(inventory)
                .fetchOne();
        return maxInventoryNumber != null ? maxInventoryNumber : 0L; // 기존 값이 없으면 0 반환
    }
}
