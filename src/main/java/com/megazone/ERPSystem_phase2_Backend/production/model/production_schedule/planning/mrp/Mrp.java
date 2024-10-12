package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToStock;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 총 소요량 = 공정별 작업계획수량 × 해당공정소요수량 + (해당공정소요수량 × 로스율)
 */

@Entity(name = "planning_mrp")
@Table(name = "planning_mrp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mrp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mrp_id", nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false, unique = true)
    private String code; // MRP코드

    @Column(nullable = false)
    private String name; // MRP명

    @Column(nullable = true)
    private String remarks; // MRP 설명

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mps_id", nullable = false)
    private Mps mps; // MPS와 연관 관계 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_data_id", nullable = false)
    private MaterialData materialData; // 자재와 연관 관계 설정

    @Column(nullable = false)
    private BigDecimal requiredQuantity; // 필요한 총 자재 수량

    @Column(nullable = false)
    private BigDecimal onHandQuantity; // 현재 재고 수량

    @Column(nullable = false)
    private BigDecimal plannedOrderQuantity; // 계획된 발주량

    @Column(nullable = false)
    private LocalDateTime plannedOrderReleaseDate; // 계획된 발주일

    @Column(nullable = false)
    private LocalDateTime plannedOrderReceiptDate; // 계획된 입고일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom standardBom; // StandardBom과 연관 관계 설정

    // 연관 엔티티에서 활용:
    // - Mps 엔티티에서 Mrp를 참조하여 생산계획에 따른 자재 소요를 파악
    // - Material 엔티티에서 Mrp를 참조하여 자재 수요와 공급 계획을 관리
}

