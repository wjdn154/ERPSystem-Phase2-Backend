package com.megazone.ERPSystem_phase2_Backend.Integrated.service;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dto.DashboardDataDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.EnvironmentalCertificationAssessmentRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDashBoardDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger.IncomeStatementService;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.SalesAndPurchaseLedgerService;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.enums.ActivityType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class IntegratedServiceImpl implements IntegratedService {

    private final RecentActivityRepository recentActivityRepository;
    private final EnvironmentalCertificationAssessmentRepository environmentalCertificationAssessmentRepository;
    private final SalesAndPurchaseLedgerService salesAndPurchaseLedgerService;
    private final IncomeStatementService incomeStatementService;
    private final WorkPerformanceRepository workPerformanceRepository;

    @Override
    public DashboardDataDTO dashboard() {

        
        List<DashboardDataDTO.ActivityDTO> activities = getActivityDTOS(); // 최근 활동        
        DashboardDataDTO.EnvironmentalScoreDTO environmentalScore = getEnvironmentalScoreDTO(); // 환경 점수        
        IncomeStatementLedgerDashBoardDTO incomeStatementLedgerDashBoardDTO = incomeStatementService.DashBoardShow(); // 매출 및 비용 추이 데이터 집계
        List<DashboardDataDTO.SalesDataDTO> salesDataList = getSalesDataDTOS(incomeStatementLedgerDashBoardDTO); // 매출 및 비용 추이 데이터 가공
        DashboardDataDTO.DashboardWidgetDTO widgets = getDashboardWidgetDTO(incomeStatementLedgerDashBoardDTO); // 총매출, 총직원수, 재고현황, 생산량

        return DashboardDataDTO.builder()
                .widgets(widgets)
                .activities(activities)
                .environmentalScore(environmentalScore)
                .salesData(salesDataList)
                .build();
    }

    private DashboardDataDTO.DashboardWidgetDTO getDashboardWidgetDTO(IncomeStatementLedgerDashBoardDTO incomeStatementLedgerDashBoardDTO) {

        BigDecimal totalWorkPerformance = workPerformanceRepository.findAll().stream().map(WorkPerformance::getAcceptableQuantity).reduce(BigDecimal.ZERO, BigDecimal::add); // 총 생산량

        DashboardDataDTO.DashboardWidgetDTO widgets = DashboardDataDTO.DashboardWidgetDTO.builder()
                .financeName("총 매출")
                .financeValue(incomeStatementLedgerDashBoardDTO.getTotalRevenue())
                .productionName("총 생산량")
                .productionValue(totalWorkPerformance)
                .build();
        return widgets;
    }

    @NotNull
    private List<DashboardDataDTO.SalesDataDTO> getSalesDataDTOS(IncomeStatementLedgerDashBoardDTO incomeStatementLedgerDashBoardDTO) {
        AtomicInteger monthIndex = new AtomicInteger(1);
        List<DashboardDataDTO.SalesDataDTO> salesDataList = incomeStatementLedgerDashBoardDTO.getIncomeStatementLedger().stream()
                .map(incomeStatementLedgerMonthList -> {
                    BigDecimal sales = incomeStatementLedgerMonthList.stream()
                            .filter(item -> item.getName().equals("매출"))
                            .map(IncomeStatementLedgerShowDTO::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal cost = incomeStatementLedgerMonthList.stream()
                            .filter(item -> item.getName().equals("비용"))
                            .map(IncomeStatementLedgerShowDTO::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return DashboardDataDTO.SalesDataDTO.builder()
                            .name(monthIndex.getAndIncrement() + "월")
                            .sales(sales)
                            .cost(cost)
                            .build();
                })
                .collect(Collectors.toList());
        return salesDataList;
    }

    @Nullable
    private DashboardDataDTO.EnvironmentalScoreDTO getEnvironmentalScoreDTO() {
        DashboardDataDTO.EnvironmentalScoreDTO environmentalScore = environmentalCertificationAssessmentRepository.findByMonth(YearMonth.now())
                .map(environmentalCertificationAssessment -> DashboardDataDTO.EnvironmentalScoreDTO.builder()
                        .totalScore(environmentalCertificationAssessment.getTotalScore())
                        .wasteScore(environmentalCertificationAssessment.getWasteScore())
                        .energyScore(environmentalCertificationAssessment.getEnergyScore())
                        .build())
                .orElse(null);
        return environmentalScore;
    }

    @NotNull
    private List<DashboardDataDTO.ActivityDTO> getActivityDTOS() {
        List<DashboardDataDTO.ActivityDTO> activities = recentActivityRepository.findAllByOrderByActivityTimeDesc()
                .stream()
                .map(activity -> DashboardDataDTO.ActivityDTO.builder()
                        .id(activity.getId())
                        .activityDescription(activity.getActivityDescription())
                        .activityType(activity.getActivityType())
                        .activityTime(calculateTimeAgo(activity.getActivityTime()))
                        .build())
                .toList();
        return activities;
    }

    private String calculateTimeAgo(LocalDateTime activityTime) {
        LocalDateTime now = LocalDateTime.now();
        long years = ChronoUnit.YEARS.between(activityTime, now);
        if (years > 0) return years + "년 전";

        long months = ChronoUnit.MONTHS.between(activityTime, now);
        if (months > 0) return months + "달 전";

        long weeks = ChronoUnit.WEEKS.between(activityTime, now);
        if (weeks > 0) return weeks + "주 전";

        long days = ChronoUnit.DAYS.between(activityTime, now);
        if (days > 0) return days + "일 전";

        long hours = ChronoUnit.HOURS.between(activityTime, now);
        if (hours > 0) return hours + "시간 전";

        long minutes = ChronoUnit.MINUTES.between(activityTime, now);
        if (minutes > 0) return minutes + "분 전";

        return "방금 전";
    }
}