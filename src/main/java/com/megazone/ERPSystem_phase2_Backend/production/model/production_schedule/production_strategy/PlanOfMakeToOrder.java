package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "production_strategy_plan_of_make_to_order")
@Table(name = "production_strategy_plan_of_make_to_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanOfMakeToOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mto_plan_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name; // 주문생산계획명

    @Column(nullable = false)
    private Integer quantity; // 생산 수량

    @Column(nullable = true)
    private BigDecimal estimatedCost; // 예상 비용 (선택 사항)

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_request_id", nullable = false)
    private ProductionRequest productionRequest; // 생산의뢰와 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mps_id", nullable = false)
    private Mps mps; // MPS와 연관 관계 추가

    @OneToMany(mappedBy = "planOfMakeToOrder")
    private List<ProductionOrder> productionOrders; // 작업지시 목록

    @Column(nullable = true)
    private String remarks; // 추가 설명

    // 연관 엔티티에서 활용:
    // - Mps 엔티티에서 MTO Plan을 참조하여 MTO 생산 계획을 관리
    // - ProductionRequest 엔티티에서 MTO Plan을 참조하여 생산의뢰가 어떻게 계획되었는지 확인
}

