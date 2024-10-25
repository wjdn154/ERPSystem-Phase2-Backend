package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.DailyAndMonthReportSearchDTO;

import java.util.List;

public interface WorkPerformanceRepositoryCustom {

    Long findProductionOrderIdByWorkPerformanceId(Long workPerformanceId);


    List<DailyAndMonthReportSearchDTO> dailyAndMonthlyReport(DailyAndMonthReportSearchDTO dto);

}
