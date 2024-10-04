package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "mrp_material_input_status")
@Table(name = "mrp_material_input_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialInputStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String name; // 현황등록명 (자동 생성 가능: 일자-제품-공정-작업장-설비 등)

    @Column(nullable = false)
    private LocalDateTime dateTime; // 일자 및 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_data_id", nullable = false)
    private MaterialData materialData; // 자재와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder; // 작업지시와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", nullable = false)
    private ProcessDetails processDetails; // 공정 단계와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id", nullable = false)
    private Workcenter workcenter; // 작업장과 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = true)
    private EquipmentData equipmentData; // 설비와 연관 관계 (필요 시)

    @Column(nullable = false)
    private BigDecimal quantityConsumed; // 소비된 자재 수량

    @Column(nullable = false)
    private String unitOfMeasure; // 자재의 단위

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

    // 비즈니스 로직:
    // - 자재 소비 후 재고 수준 업데이트
    // - 재고 수준이 재주문점 이하로 감소하면 자동으로 출고 요청 생성
}
