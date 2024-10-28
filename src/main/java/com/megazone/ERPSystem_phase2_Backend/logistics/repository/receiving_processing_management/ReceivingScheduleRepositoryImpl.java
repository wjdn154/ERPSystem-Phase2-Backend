package com.megazone.ERPSystem_phase2_Backend.logistics.repository.receiving_processing_management;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.QReceivingSchedule.receivingSchedule;


@Repository
@RequiredArgsConstructor
public class ReceivingScheduleRepositoryImpl implements ReceivingScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findMaxPendingInventoryNumber() {
        Long maxPendingInventoryNumber = queryFactory
                .select(receivingSchedule.pendingInventoryNumber.max())
                .from(receivingSchedule)
                .fetchOne();
        return maxPendingInventoryNumber != null ? maxPendingInventoryNumber : 0L;
    }

    @Override
    public Long findMaxReceivingDateNumberByDate(LocalDate receivingDate) {
        Long maxDateNumber = queryFactory
                .select(receivingSchedule.receivingDateNumber.max())
                .from(receivingSchedule)
                .where(receivingSchedule.receivingDate.eq(receivingDate))
                .fetchOne();

        return maxDateNumber;
    }
}