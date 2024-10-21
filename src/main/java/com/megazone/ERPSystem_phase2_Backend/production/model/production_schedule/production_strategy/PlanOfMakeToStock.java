package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Sale;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "production_schedule_plan_of_make_to_stock")
@Table(name = "production_schedule_plan_of_make_to_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanOfMakeToStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mts_plan_id", nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String name; // 재고생산계획명

    @Column(nullable = false)
    private Integer quantity; // 생산 수량

    @Column(nullable = true)
    private BigDecimal estimatedCost; // 예상 비용 (선택 사항)

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "forecast_id", nullable = false)
//    private Sale sale; // 판매 예측 혹은 계획과 연관 관계 추가
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mps_id", nullable = false)
//    private Mps mps; // MPS와 연관 관계 추가
//
//    @OneToMany(mappedBy = "planOfMakeToStock")
//    private List<ProductionOrder> productionOrders; // 작업지시 목록

    @Column(nullable = true)
    private String remarks; // 추가 설명

    // 연관 엔티티에서 활용:
    // - Mps 엔티티에서 MTS Plan을 참조하여 MTS 생산 계획을 관리
    // - Sale 엔티티에서 MTS Plan을 참조하여 예측된 수요가 어떻게 생산 계획에 반영되었는지 확인
}
