package com.megazone.ERPSystem_phase2_Backend.Integrated.model.dto;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDataDTO {

    private List<SalesDataDTO> salesData;
    private DashboardWidgetDTO widgets;
    private EnvironmentalScoreDTO environmentalScore;
    private List<ActivityDTO> activities;
    private List<ProductionStatusDTO> productionStatuses;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesDataDTO {
        private String name; // 월 이름
        private BigDecimal sales; // 매출
        private BigDecimal cost; // 비용
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardWidgetDTO {
        private String financeName;
        private BigDecimal financeValue;
        private String hrName;
        private BigDecimal hrValue;
        private String logisticsName;
        private BigDecimal logisticsValue;
        private String productionName;
        private BigDecimal productionValue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalScoreDTO {
        private int totalScore; // 친환경 인증 점수
        private int wasteScore; // 폐기물 점수
        private int energyScore; // 에너지 점수
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityDTO {
        private Long id;
        private ActivityType activityType; // 활동 유형 (재무회계, 인사관리, 생산관리, 물류관리)
        private String activityDescription; // 활동 내용
        private String activityTime; // 활동 발생 시간
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductionStatusDTO {
        private String name; // 상태 이름 (예: 생산 중)
        private int percentage; // 백분율 (예: 65)
        private String color; // 색상 클래스명 (예: bg-blue-500)
    }
}