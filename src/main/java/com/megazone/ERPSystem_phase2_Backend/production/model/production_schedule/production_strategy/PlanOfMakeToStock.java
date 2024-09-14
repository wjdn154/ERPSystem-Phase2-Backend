package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false)
    private String name; // 재고생산계획명

    @Column(nullable = false)
    private BigDecimal goalQuantity; // 목표수량

    @Column(nullable = false)
    private BigDecimal estimatedCost; // 예상 비용

    @Column(nullable = false)
    private LocalDate startDate; // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate; // 종료 날짜

    @OneToMany(mappedBy = "planOfMakeToStock")
    @Column(nullable = true)
    private List<ProductionOrder> productionOrders;

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고
}
