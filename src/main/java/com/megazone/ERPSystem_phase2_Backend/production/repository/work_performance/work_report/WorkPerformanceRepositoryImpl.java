package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.QProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.QProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.QMps;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.QWorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.DailyAndMonthlyReportSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.DailyReportDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.MonthlyReportDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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

    @Override
    public List<DailyReportDTO> dailyReport(DailyAndMonthlyReportSearchDTO dto) {

        QWorkPerformance wp = QWorkPerformance.workPerformance;
        QProductionOrder po = QProductionOrder.productionOrder;
        QMps pm = QMps.mps;
        QProduct p = QProduct.product;
        QProcessDetails pd = QProcessDetails.processDetails;

        return queryFactory
                .select(Projections.fields(
                        DailyReportDTO.class,
                        p.code.as("productCode"),
                        p.name.as("productName"),
                        p.standard.as("productStandard"),
                        p.unit.as("productUnit"),
                        wp.quantity.as("totalQuantity"),
//                        p.salesPrice.as("productSalesPrice"),
                        pd.cost.as("processCost"),
                        wp.acceptableQuantity.as("acceptableQuantity"),
                        pd.cost.divide(wp.quantity).multiply(wp.acceptableQuantity).as("acceptableAmount"),
                        wp.defectiveQuantity.as("defectiveQuantity"),
                        pd.cost.divide(wp.quantity).multiply(wp.defectiveQuantity).as("defectiveAmount"),
                        wp.averageWasteGenerated.as("averageWasteGenerated"),
                        wp.wasteGenerated.as("wasteGenerated"),
                        wp.wasteGeneratedPercentage.as("wasteGeneratedPercentage"),
                        wp.averageEnergyConsumed.as("averageEnergyConsumed"),
                        wp.energyConsumed.as("energyConsumed"),
                        wp.energyConsumedPercentage.as("energyConsumedPercentage")
                ))
                .from(wp)
                .join(po).on(wp.productionOrder.id.eq(po.id))
                .join(pm).on(po.mps.id.eq(pm.id))
                .join(p).on(pm.product.id.eq(p.id))
                .join(pd).on(po.processDetails.id.eq(pd.id))
                .where(wp.workDate.between(dto.getStartDate(), dto.getEndDate()))
                .fetch();
    }

    @Override
    public List<MonthlyReportDTO> monthlyReport(DailyAndMonthlyReportSearchDTO dto) {
        QWorkPerformance wp = QWorkPerformance.workPerformance;
        QProductionOrder po = QProductionOrder.productionOrder;
        QMps pm = QMps.mps;
        QProduct p = QProduct.product;

        return queryFactory
                .select(Projections.fields(
                        MonthlyReportDTO.class,
                        p.code.as("productCode"),
                        p.name.as("productName"),
                        p.standard.as("productStandard"),
                        p.unit.as("productUnit"),
                        wp.quantity.sum().as("totalPlanned"),
                        wp.acceptableQuantity.sum().as("totalActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(1)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("janPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(1)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("janActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(2)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("febPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(2)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("febActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(3)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("marPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(3)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("marActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(4)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("aprPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(4)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("aprActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(5)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("mayPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(5)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("mayActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(6)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("junPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(6)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("junActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(7)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("julPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(7)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("julActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(8)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("augPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(8)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("augActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(9)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("sepPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(9)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("sepActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(10)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("octPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(10)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("octActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(11)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("novPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(11)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("novActual"),
                        new CaseBuilder().when(wp.workDate.month().eq(12)).then(wp.quantity).otherwise(BigDecimal.ZERO).sum().as("decPlanned"),
                        new CaseBuilder().when(wp.workDate.month().eq(12)).then(wp.acceptableQuantity).otherwise(BigDecimal.ZERO).sum().as("decActual")
                ))
                .from(wp)
                .join(po).on(wp.productionOrder.id.eq(po.id))
                .join(pm).on(po.mps.id.eq(pm.id))
                .join(p).on(pm.product.id.eq(p.id))
                .where(wp.workDate.between(
                        dto.getStartDate().withDayOfMonth(1),
                        dto.getEndDate().withDayOfMonth(dto.getEndDate().lengthOfMonth())))
                .groupBy(p.code, p.name, p.standard, p.unit)
                .fetch();
    }
}
