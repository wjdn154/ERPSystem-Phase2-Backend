package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "production_schedule_production_requests")
@Table(name = "production_schedule_production_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRequests {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; //pk

    @Column(nullable = false)
    private String name; // 생산의뢰명

    @Column(nullable = false)
    private Boolean isConfirmed; // 확정여부

    @Column(nullable = false)
    private LocalDate requestDate; // 요청일자

    @Column(nullable = false)
    private BigDecimal requestQuantity; // 요청수량 (변경불가)

    @Column(nullable = false)
    private BigDecimal confirmedQuantity; // 확정수량

    @OneToMany(mappedBy = "productionRequest")
    private List<PlanOfMakeToOrder> planOfMakeToOrders; // 연관 주문생산계획

    @Column(nullable = false)
    private String product; // TODO 연관 제품

    @Column(nullable = false)
    private String salesOrder; // TODO 연관 영업 주문

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "requester_id", nullable = false)
    @Column(nullable = false)
    private String requester; // 요청자, 부서 (dto - name, entity - id) // TODO 직원 연관관계

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고
}
