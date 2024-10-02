package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.InboundRegistration;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**품질검사
 * 특정 생산 라인의 제품을 전체적으로 검사하는 절차
 * */
@Entity(name = "production_quality_inspection")
@Table(name = "production_quality_inspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String inspectionCode; // 지정검사코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String inspectionName; // 실적검사명

    private String description; // 검사 설명

    @Column(nullable = false)
    private Boolean isPassed; // 검사통과여부

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QualityInspectionType qualityInspectionType;    //품질 검사 유형

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "work_performance_id")
    private WorkPerformance workPerformance;     // 연관 작업실적 WorkPerformance

    @OneToMany(mappedBy = "qualityInspections")
    private List<DefectCategory> defectCategories;     // 불량유형

    @OneToMany(mappedBy = "qualityInspection")
    private List<InboundRegistration> inboundRegistrations;   //품질검사에 따른 제품 입고 리스트

}
