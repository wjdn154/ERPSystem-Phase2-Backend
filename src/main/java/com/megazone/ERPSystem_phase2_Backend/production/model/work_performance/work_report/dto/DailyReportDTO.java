package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.enums.DailyAndMonthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDTO {
    private String productCode;            // 제품 코드
    private String productName;            // 제품 이름
    private String productStandard;        // 제품 규격
    private String productUnit;            // 단위
    private BigDecimal totalQuantity;      // 총 수량
//    private BigDecimal productSalesPrice;  // 판매 가격
    private BigDecimal processCost;        // 공정 전체 금액
    private BigDecimal acceptableQuantity; // 양품 수량
    private BigDecimal acceptableAmount;   // 양품 금액
    private BigDecimal defectiveQuantity;  // 불량 수량
    private BigDecimal defectiveAmount;    // 불량 금액
    private BigDecimal industryAverageWasteGenerated; // 산업 평균 폐기물 발생량
    private BigDecimal wasteGenerated;     // 폐기물 발생량
    private BigDecimal wasteGeneratedPercentage; // 산업 평균 대비 폐기물 발생량 비율
    private BigDecimal industryAverageEnergyConsumed; // 산업 평균 에너지 소비량
    private BigDecimal energyConsumed;     // 에너지 소비량
    private BigDecimal energyConsumedPercentage; // 산업 평균 대비 에너지 소비량 비율
}
