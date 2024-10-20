package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee.employee;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.QInventoryInspection.inventoryInspection;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.QInventoryInspectionDetail.inventoryInspectionDetail;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;

@Repository
@RequiredArgsConstructor
public class InventoryInspectionRepositoryImpl implements InventoryInspectionRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<InventoryInspection> findInspectionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .selectFrom(inventoryInspection)
                .leftJoin(inventoryInspection.details, inventoryInspectionDetail).fetchJoin()
                .leftJoin(inventoryInspection.employee, employee).fetchJoin()
                .leftJoin(inventoryInspection.warehouse, warehouse).fetchJoin()
                .where(inventoryInspection.inspectionDate.between(startDate, endDate))  // 날짜 범위 필터링
                .orderBy(inventoryInspection.inspectionDate.desc(), inventoryInspection.inspectionNumber.desc())  // 날짜 및 전표 번호 기준 내림차순 정렬
                .fetch();
    }

    @Override
    public Long findMaxInspectionNumberByDate(LocalDate inspectionDate) {
        Long maxInspectionNumber = queryFactory
                .select(inventoryInspection.inspectionNumber.max())
                .from(inventoryInspection)
                .where(inventoryInspection.inspectionDate.eq(inspectionDate))
                .fetchOne();

        // 최대 전표 번호 반환
        return maxInspectionNumber;
    }

}
