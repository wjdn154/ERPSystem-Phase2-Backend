package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QHierarchyGroup.hierarchyGroup;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouseHierarchyGroup.warehouseHierarchyGroup;

@Repository
@RequiredArgsConstructor
public class WarehouseRepositoryImpl implements WarehouseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 모든 창고를 조회함.
     * @return 창고 목록을 반환함.
     */
    @Override
    public List<WarehouseDTO> findAllWarehouse() {
        return queryFactory
                .select(Projections.fields(WarehouseDTO.class,
                        warehouse.code.as("code"),
                        warehouse.name.as("name"),
                        warehouse.warehouseType.as("warehouseType"),
                        warehouse.productionProcess.as("productionProcess"),
                        warehouse.isActive.as("isActive")
                        ))
                .from(warehouse)
                .orderBy(Expressions.stringTemplate("LENGTH({0})", warehouse.code).asc(),
                        warehouse.code.asc())
                .fetch();
    }

    @Override
    public WarehouseDetailDTO getWarehouseDetail(Long id) {
        // 창고코드, 창고명, 창고타입, 영업단가그룹(예정), 구매단가그룹(예정), 창고계층그룹, 창고사용여부
        WarehouseDetailDTO warehouseDetail = queryFactory
                .select(Projections.fields(WarehouseDetailDTO.class,
                        warehouse.code.as("code"),
                        warehouse.name.as("name"),
                        warehouse.warehouseType.as("warehouseType"),
                        warehouse.productionProcess.as("productionProcess"),
                        warehouse.isActive.as("isActive")
                        ))
                .from(warehouse)
                .where(warehouse.id.eq(id))
                .fetchOne();

        List<HierarchyGroupDTO> hierarchyGroups = queryFactory
                .select(Projections.fields(HierarchyGroupDTO.class,
                        hierarchyGroup.id.as("id"),
                        hierarchyGroup.hierarchyGroupCode.as("code"),
                        hierarchyGroup.hierarchyGroupName.as("name")
                        ))
                .from(warehouseHierarchyGroup)
                .leftJoin(warehouseHierarchyGroup.hierarchyGroup, hierarchyGroup)
                .where(warehouseHierarchyGroup.warehouse.id.eq(id))
                .fetch();

        warehouseDetail.setHierarchyGroupList(hierarchyGroups);

        return warehouseDetail;
    }


}
