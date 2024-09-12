package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom;

/**
 * Green BOM은 제품의 전체 수명 주기 동안 환경 영향을 최소화하고, 재활용 및 재사용 가능성을 높이며, 유해 물질을 관리하는 데 중점을 둡니다.
 * Standard Bom을 참조하여 기존 필드를 모두 포함하되, 친환경 관련 필드들을 추가함.
 */

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardousMaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "bom_green_bom")
@Table(name = "bom_green_bom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreenBom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom standardBom; // GreenBom이 참조하는 StandardBom

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<HazardousMaterialType> hazardousMaterials; // 유해 물질 정보

    @Column(nullable = false)
    private Boolean recyclable = false; // 재활용 가능 여부, 기본값 false

    @Column(nullable = false)
    private Boolean reusable = false; // 재사용 가능 여부, 기본값 false

    @Column(nullable = true)
    private BigDecimal energyConsumption; // 에너지 소비량

    @Column(nullable = true)
    private BigDecimal carbonFootprint; // 탄소 발자국

    @Column(nullable = true)
    private String ecoCertification; // 친환경 인증 정보
}
