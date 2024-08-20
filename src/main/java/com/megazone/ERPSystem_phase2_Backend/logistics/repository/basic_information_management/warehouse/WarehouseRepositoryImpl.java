package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDetailDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;

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



}
