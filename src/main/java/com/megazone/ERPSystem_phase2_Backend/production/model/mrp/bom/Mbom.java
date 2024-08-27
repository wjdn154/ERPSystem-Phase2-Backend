package com.megazone.ERPSystem_phase2_Backend.production.model.mrp.bom;

/**
 * MBOM은 주로 제조 공정에서 필요한 부품, 공정, 자재 등의 세부 정보를 관리합니다.
 * 이를 위해 MBom 엔티티를 생성하고, StandardBom을 참조하면서 제조에 필요한 추가적인 필드들을 관리합니다.
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "bom_mbom")
@Table(name = "bom_mbom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mbom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    // MBom이 참조하는 StandardBom
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom standardBom;

    @Column(nullable = true)
    private String manufacturingProcess; // 제조 공정 정보

    @Column(nullable = true)
    private String machineTools; // 사용되는 기계 및 도구 정보

    @Column(nullable = true)
    private Double productionTime; // 제조에 소요되는 시간

    @Column(nullable = true)
    private Double laborCost; // 인건비

    @Column(nullable = true)
    private Double overheadCost; // 간접 비용

    @Column(nullable = true)
    private Boolean isOutsourced; // 외주 여부

    @Column(nullable = true)
    private String assemblyInstructions; // 조립 지침
}
