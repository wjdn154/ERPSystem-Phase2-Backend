package com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard;

import com.megazone.ERPSystem_phase2_Backend.common.config.YearMonthConverter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;



/**
 * 친환경 인증 평가 엔티티
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "environmental_certification_assessment")
@Table(name = "environmental_certification_assessment")
public class EnvironmentalCertificationAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = YearMonthConverter.class)
    @Column(nullable = false, unique = true)
    private YearMonth month; // 집계 대상 월

    @Column(nullable = false) private BigDecimal totalWasteGenerated; // 해당 월 동안의 총 폐기물 발생량(KG)
    @Column(nullable = false) private BigDecimal totalEnergyConsumed; // 해당 월 동안의 총 에너지 사용량(MJ)
    @Column(nullable = false) private BigDecimal totalIndustryAverageWasteGenerated; // 산업 평균 폐기물 발생량(KG)
    @Column(nullable = false) private BigDecimal totalIndustryAverageEnergyConsumed; // 산업 평균 에너지 사용량(MJ)
    @Column(nullable = false) private Integer wasteScore; // 폐기물 항목 점수 (0~100)
    @Column(nullable = false) private Integer energyScore; // 에너지 항목 점수 (0~100)
    @Column(nullable = false) private Integer totalScore; // 최종 점수 (0~100, 가중치 반영)
    @Column(nullable = false) private LocalDateTime createdDate; // 데이터 생성 일자
    @Column(nullable = false) private LocalDateTime modifiedDate; // 데이터 수정 일자

}