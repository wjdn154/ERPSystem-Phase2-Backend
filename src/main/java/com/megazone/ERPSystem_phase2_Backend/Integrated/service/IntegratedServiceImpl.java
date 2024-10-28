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
import lombok.RequiredArgsConstructor;
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

    @Override
    public DashboardDataDTO dashboard() {

        // 최근 활동
        List<DashboardDataDTO.ActivityDTO> activities = recentActivityRepository.findAllByOrderByActivityTimeDesc()
                .stream()
                .map(activity -> DashboardDataDTO.ActivityDTO.builder()
                        .id(activity.getId())
                        .activityDescription(activity.getActivityDescription())
                        .activityType(activity.getActivityType())
                        .activityTime(calculateTimeAgo(activity.getActivityTime()))
                        .build())
                .toList();

        // 환경 점수
        DashboardDataDTO.EnvironmentalScoreDTO environmentalScore = environmentalCertificationAssessmentRepository.findByMonth(YearMonth.now())
                .map(environmentalCertificationAssessment -> DashboardDataDTO.EnvironmentalScoreDTO.builder()
                        .totalScore(environmentalCertificationAssessment.getTotalScore())
                        .wasteScore(environmentalCertificationAssessment.getWasteScore())
                        .energyScore(environmentalCertificationAssessment.getEnergyScore())
                        .build())
                .orElse(null);


        AtomicInteger monthIndex = new AtomicInteger(1);
        IncomeStatementLedgerDashBoardDTO incomeStatementLedgerDashBoardDTO = incomeStatementService.DashBoardShow();
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

        DashboardDataDTO.DashboardWidgetDTO widgets = DashboardDataDTO.DashboardWidgetDTO.builder()
                .financeName("총 매출")
                .financeValue(incomeStatementLedgerDashBoardDTO.getTotalRevenue())
                .build();


        return DashboardDataDTO.builder()
                .widgets(widgets)
                .activities(activities)
                .environmentalScore(environmentalScore)
                .salesData(salesDataList)
                .build();
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