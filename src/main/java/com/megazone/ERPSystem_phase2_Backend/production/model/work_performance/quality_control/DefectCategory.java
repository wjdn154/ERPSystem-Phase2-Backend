package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 불량유형 엔티티
 */

@Entity(name = "quality_control_defect_category")
@Table(name = "quality_control_defect_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @Column(nullable = false)
    private String code;     // 불량군코드

    @Column(nullable = false)
    private String name; // 불량군명

    @Column(nullable = false)
    private Boolean isUsed;    // 사용여부

    @Column(nullable = true)
    private String remarks;    // 불량군설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_type_id")
    private DefectType defectType;     // 연관 불량유형 목록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_inspection_id")
    private QualityInspection qualityInspections; // 연관 품질검사
}
