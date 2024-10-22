package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.QWorkPerformance.workPerformance;

@Repository
@AllArgsConstructor
public class WorkPerformanceRepositoryImpl implements WorkPerformanceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findProductionOrderIdByWorkPerformanceId(Long workPerformanceId) {
        return queryFactory
                .select(workPerformance.productionOrder.id)
                .from(workPerformance)
                .where(workPerformance.id.eq(workPerformanceId))
                .fetchOne();
    }
}
