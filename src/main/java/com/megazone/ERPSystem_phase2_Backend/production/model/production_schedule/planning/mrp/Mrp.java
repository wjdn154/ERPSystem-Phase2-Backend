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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mps_id", nullable = false)
//    private Mps mps; // MPS와 연관 관계 설정 ( MPS 자체를 보류 )

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_data_id", nullable = false)
    private MaterialData materialData; // 자재와 연관 관계 설정

    @Column(nullable = false)
    private BigDecimal requiredQuantity; // 필요한 총 자재 수량 TODO sbom에서 땡겨올지 아니면 직접 지정할지?

    @Column(nullable = false)
    private BigDecimal onHandQuantity; // 현재 재고 수량 -> todo relate to Inventory

    @Column(nullable = false)
    private BigDecimal plannedOrderQuantity; // 계획된 발주량 -> todo relate to 발주계획서?

    @Column(nullable = false)
    private LocalDateTime plannedOrderReleaseDate; // 계획된 발주일 -> todo relate to 발주계획서?

    @Column(nullable = false)
    private LocalDateTime plannedOrderReceiptDate; // 계획된 입고일 -> todo relate to 입고지시서 ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom standardBom; // StandardBom과 연관 관계 설정 -> TODO 어떠한 품목을 제조하는 데 어떤 자재가 얼마나 필요한지 명세서이므로, 필요한 자재수량

    // TODO MaterialInputStatus Entity와 연관관계 설정하여 현재 진행되고 있는 작업지시에서 실자재투입량이 얼마나되는지 갖고오기

    // 연관 엔티티에서 활용:
    // - (MPS 보류) Mps 엔티티에서 Mrp를 참조하여 생산계획에 따른 자재 소요를 파악
    // - Material 엔티티에서 Mrp를 참조하여 자재 수요와 공급 계획을 관리
    //
}

