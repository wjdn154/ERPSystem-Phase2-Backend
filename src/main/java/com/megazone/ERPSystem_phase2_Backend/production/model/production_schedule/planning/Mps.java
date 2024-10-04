package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "plan_of_production_mps")
@Table(name = "plan_of_production_mps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mps_id", nullable = false)
    private Long id; // PK

    @Column(nullable = false)
    private String name; // MPS명

    @Column(nullable = false)
    private LocalDate planDate; // 계획 수립 날짜

    @Column(nullable = false)
    private String planType; // 계획 유형 (MTO, MTS)

    @Column(nullable = false)
    private LocalDate startDate; // 생산 시작일

    @Column(nullable = false)
    private LocalDate endDate; // 생산 완료 예정일

    @Column(nullable = false)
    private String status; // 계획 상태 (계획, 확정, 진행 중, 완료)

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 생산할 제품 (Product 엔티티와 연관)

    @Column(nullable = false)
    private Integer quantity; // 생산 수량

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = true)
    private Orders orders; // 관련된 고객주문 (SalesOrder 엔티티와 연관)

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale; // 관련된 판매계획 (Sale 엔티티와 연관)

    // 연관 엔티티에서 활용:
    // - Product 엔티티에서 MPS를 참조하여 생산 스케줄을 확인할 수 있음.
    // - SalesOrder 엔티티에서 MPS를 참조하여 주문이 어떻게 생산계획에 반영되었는지 추적 가능.
    // - Sale 엔티티에서 MPS를 참조하여 예측된 수요가 생산계획에 어떻게 반영되었는지 확인 가능.
}

