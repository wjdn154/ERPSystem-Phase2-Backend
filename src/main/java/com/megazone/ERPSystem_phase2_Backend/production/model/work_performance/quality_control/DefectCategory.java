package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 불량유형 등록을 위한 불량군(대분류) 엔티티
 */

@Entity
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

    @OneToMany(mappedBy = "defectCategory", fetch = FetchType.LAZY)
    private List<DefectType> defectTypeList;     // 연관 불량유형 목록
}
