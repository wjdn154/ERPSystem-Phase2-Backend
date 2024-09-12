package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.InboundRegistration;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // 지정검사코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // 실적검사명

    @Column(nullable = true)
    private String description; // 검사 설명

    @Column(nullable = false)
    private Boolean isPassed; // 검사통과여부

    @OneToMany(mappedBy = "qualityInspections")
    private List<DefectCategory> defectCategories;     // 불량유형

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_performance_id")
    private WorkPerformance workPerformance;     // 연관 작업실적 WorkPerformance

    @OneToMany(mappedBy = "qualityInspection")
    private List<InboundRegistration> inboundRegistrations;

}
