package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**작업 실적 리스트 dto
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkPerformanceUpdateDTO {
    private Long productionOrderId; // 작업 지시 ID
    private Long workers; // 작업 인원
    private BigDecimal quantity; // 실제 총 생산량
    private BigDecimal defectiveQuantity; // 불량 수량
    private BigDecimal acceptableQuantity; // 양품 수량
    private LocalDate workDate; // 작업 시간
    private LocalDateTime actualStartDateTime; // 작업 종료 시간
    private LocalDateTime actualEndDateTime; // 작업 종료 시간
}
