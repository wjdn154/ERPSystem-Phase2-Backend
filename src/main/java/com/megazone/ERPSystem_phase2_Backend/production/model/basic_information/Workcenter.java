package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작업장 정보 테이블
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workcenter {

    // public enum WorkcenterType { PRODUCTION, ASSEMBLY, QUALITY_CONTROL, PACKAGING, MAINTENANCE, R_AND_D, TEST, LOGISTICS };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // 작업장코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // 작업장명

    @Column(nullable = true)
    private String description; // 작업장 설명

    @Column(nullable = true)
    private Long inputPersonnel; // 투입인원수

    @Column(nullable = false)
    private boolean active;


/*
    @Column(nullable = false)
    // 공장 엔티티 from 물류의 공장

    @Column(nullable = false)
    // 생산공정 엔티티 from 생산의 공정

    @Column(nullable = false)
    // 작업자원(자재) 엔티티 from 생산의 자재

    @Column(nullable = false)
    // 책임자(담당자) 엔티티 from 인사의 직원
*/
}
