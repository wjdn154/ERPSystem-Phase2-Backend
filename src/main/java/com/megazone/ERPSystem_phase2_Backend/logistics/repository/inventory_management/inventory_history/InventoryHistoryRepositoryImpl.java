package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_history;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.QInventoryHistory.inventoryHistory;

@Repository
@RequiredArgsConstructor
public class InventoryHistoryRepositoryImpl implements InventoryHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findMaxWorkNumberByDate(LocalDate workDate) {
        return queryFactory
                .select(inventoryHistory.workNumber.max())  // 최대 작업 번호 조회
                .from(inventoryHistory)
                .where(inventoryHistory.workDate.eq(workDate))  // 작업 일자 조건
                .fetchOne();  // 단일 결과 반환
    }

    @Override
    public Long findMaxSlipNumberByDate(LocalDate slipDate) {
        return queryFactory
                .select(inventoryHistory.slipNumber.max())  // 최대 전표 번호 조회
                .from(inventoryHistory)
                .where(inventoryHistory.slipDate.eq(slipDate))  // 전표 일자 조건
                .fetchOne();  // 단일 결과 반환
    }
}
