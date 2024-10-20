package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseHierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.QHierarchyGroup.hierarchyGroup;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.QWarehouseHierarchyGroup.warehouseHierarchyGroup;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;
import static com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.QProcessDetails.processDetails;


@Repository
@RequiredArgsConstructor
public class WarehouseRepositoryImpl implements WarehouseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WarehouseResponseListDTO> findWarehouseList() {
        return queryFactory
                .select(Projections.fields(
                        WarehouseResponseListDTO.class,
                        warehouse.id,
                        warehouse.code,
                        warehouse.name,
                        warehouse.warehouseType.stringValue().as("warehouseType"),
                        processDetails.name.as("productionProcess"),
                        warehouse.isActive
                ))
                .from(warehouse)
                .leftJoin(warehouse.processDetail, processDetails)
                .fetch();
    }

    @Override
    public Optional<WarehouseResponseTestDTO> findWarehouseDetailById(Long warehouseId) {
        WarehouseResponseTestDTO warehouseDetail = queryFactory
                .select(Projections.fields(
                        WarehouseResponseTestDTO.class,
                        warehouse.id,
                        warehouse.code,
                        warehouse.name,
                        warehouse.warehouseType,
                        warehouse.processDetail.name.as("productionProcess"),
                        warehouse.isActive
                ))
                .from(warehouse)
                .leftJoin(warehouse.processDetail, processDetails)
                .leftJoin(warehouse.warehouseHierarchyGroup, warehouseHierarchyGroup)
                .leftJoin(warehouseHierarchyGroup.hierarchyGroup, hierarchyGroup)
                .where(warehouse.id.eq(warehouseId))
                .fetchOne();

        if (warehouseDetail == null) {
            return Optional.empty();
        }

        // 계층 그룹 정보 추가
        List<WarehouseHierarchyGroupDTO> hierarchyGroups = queryFactory
                .select(Projections.fields(
                        WarehouseHierarchyGroupDTO.class,
                        hierarchyGroup.id,
                        hierarchyGroup.hierarchyGroupCode.as("code"),
                        hierarchyGroup.hierarchyGroupName.as("name")
                ))
                .from(warehouseHierarchyGroup)
                .leftJoin(warehouseHierarchyGroup.hierarchyGroup, hierarchyGroup)
                .where(warehouseHierarchyGroup.warehouse.id.eq(warehouseId))
                .fetch();

        warehouseDetail.setHierarchyGroups(hierarchyGroups);

        return Optional.of(warehouseDetail);
    }

}
