package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.QWarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HierarchyGroupRepositoryImpl implements HierarchyGroupRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Warehouse> findWarehousesByHierarchyGroupId(Long groupId) {
        QWarehouse warehouse = QWarehouse.warehouse;
        QWarehouseHierarchyGroup warehouseHierarchyGroup = QWarehouseHierarchyGroup.warehouseHierarchyGroup;
        return queryFactory
                .selectFrom(warehouse)
                .join(warehouse.warehouseHierarchyGroup, warehouseHierarchyGroup)
                .where(warehouseHierarchyGroup.hierarchyGroup.id.eq(groupId))
                .fetch();
    }
}
