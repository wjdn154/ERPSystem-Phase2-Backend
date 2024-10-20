package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspectionDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.QInventoryInspection.inventoryInspection;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.QInventoryInspectionDetail.inventoryInspectionDetail;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct.product;

@Repository
@RequiredArgsConstructor
public class InventoryInspectionDetailRepositoryImpl implements InventoryInspectionDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 실사 항목 목록 조회 서비스
    @Override
    public List<InventoryInspectionDetail> findByInspectionDateBetween(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .selectFrom(inventoryInspectionDetail)
                .leftJoin(inventoryInspectionDetail.product, product).fetchJoin()
                .leftJoin(inventoryInspectionDetail.inventoryInspection, inventoryInspection).fetchJoin()
                .where(inventoryInspectionDetail.inventoryInspection.inspectionDate.between(startDate, endDate))  // 날짜 범위 필터링
                .orderBy(inventoryInspectionDetail.inventoryInspection.inspectionDate.desc(), inventoryInspectionDetail.inventoryInspection.inspectionNumber.desc())  // 날짜 및 전표 번호 기준 내림차순 정렬
                .fetch();
    }
}
