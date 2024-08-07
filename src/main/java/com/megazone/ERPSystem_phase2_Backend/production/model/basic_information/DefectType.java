package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 품질검사 기준이 되는 불량유형 엔티티
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;     // Long Id

    @Column(nullable = false)
    private String code;     // 불량유형코드

    @Column(nullable = false)
    private String name;     // 불량유형명

    @Column(nullable = false)
    private Boolean isUsed;     // 사용여부

    @ManyToOne(fetch = FetchType.LAZY)
    private DefectCategory defectCategory; // 연관 불량군
}
