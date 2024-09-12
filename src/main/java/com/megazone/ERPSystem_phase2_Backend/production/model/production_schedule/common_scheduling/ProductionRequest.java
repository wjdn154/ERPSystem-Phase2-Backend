package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "common_scheduling_production_requests")
@Table(name = "common_scheduling_production_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; //pk

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionRequestType requestType; // 생산의뢰구분

    @Column(nullable = false)
    private ProgressType progressType; // 진행상태구분

    @Column(nullable = true)
    private String name; // 생산의뢰명

    @Column(nullable = false)
    private Boolean isConfirmed; // 확정여부

    @Column(nullable = false)
    private LocalDate requestDate; // 의뢰등록일자 - 자동생성되나, 변경가능

    @Column(nullable = true)
    private LocalDate deadlineOfCompletion;    // 완료요청일자 ( 고객/내부부서가 생산부서에 요청하는 생산공정완료일자 - 조정 가능. 납기일보다 완료요청일자가 빨라야 함 )

    @Column(nullable = true)
    private LocalDate dueDateToProvide; // 납기일: 실제 생산된 제품을 검사, 포장, 배송 등 완료 후 납품해야 하는 일자 ( 고객에게 제품 전달해야 하는 최종 기한 - 조정 거의 불가, 변경 시 계약조건 위반으로 페널티 )

    @Column(nullable = true)
    private String client; // TODO 거래처 연관관계 ( 고객사명 등 )
//    private Client client;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id", nullable = false)
    @Column(nullable = true)
    private String productionDepartment;
//    private Department productionDepartment;     // TODO 생산부서 ( 요청 품목 생산을 담당하는 부서 ? )

    @Column(nullable = false)
    private BigDecimal requestQuantity; // 요청수량

    @Column(nullable = false)
    private BigDecimal confirmedQuantity; // TODO 확정수량 ( = Capacity에 따른 Return to Forecast 반영 )

    @OneToMany(mappedBy = "productionRequest")
    private List<PlanOfMakeToOrder> planOfMakeToOrders; // TODO 연관 주문생산계획 : 계획 등록 시 생산의뢰에 자동으로 반영됨. 생산계획부서

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
    @Column(nullable = false)
    private String product;
//    private Product product; // TODO 연관 품목 ( req -> product 단방향 ? 품명, 품번, 규격 )

    @Column(nullable = false)
    private String salesOrder;
//    private SalesOrder salesOrder; // TODO 연관 영업 주문 ( 수주 번호 , 판매단위, P/O No., 판매계획 )

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "requester_id", nullable = false)
    @Column(nullable = false)
    private String requester;
//    private Employee requester; // TODO 요청자, 부서 (dto - name, entity - id) // TODO 직원 연관관계 ( 의뢰자 )

    @Column(nullable = true)
    private String remarks; // 특이사항
}