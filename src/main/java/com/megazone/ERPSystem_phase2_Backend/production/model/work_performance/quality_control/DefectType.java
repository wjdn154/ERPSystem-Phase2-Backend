package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 품질검사 기준이 되는 불량유형 엔티티
 */

@Entity(name = "quality_control_defect_type")
@Table(name = "quality_control_defect_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;     // Long Id

    @Column(nullable = false)
    private String code;     // 불량유형코드

    @Column(nullable = false)
    private String name;     // 불량유형명

    @Column(nullable = false)
    private Boolean isUsed;     // 사용여부


    @OneToMany(mappedBy = "defectType")
    private List<DefectCategory> defectCategories; // 연관 불량군
}
