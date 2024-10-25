package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.DailyAndMonthlyReportSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.DailyReportDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.MonthlyReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface WorkPerformanceRepositoryCustom {

    Long findProductionOrderIdByWorkPerformanceId(Long workPerformanceId);


    List<DailyReportDTO> dailyReport(DailyAndMonthlyReportSearchDTO dto);
    List<MonthlyReportDTO> monthlyReport(DailyAndMonthlyReportSearchDTO dto);

}
