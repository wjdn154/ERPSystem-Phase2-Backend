package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 발주요청 테이블
 * 발주요청서에 대한 정보가 있는 테이블
 * 각 부서 담당자는 구매 관리 부서에 발주를 요청하기 위해 발주 요청을 한다.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주 요청 상세와의 일대다 관계
    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseRequestDetail> purchaseRequestDetails = new ArrayList<>();

//    // 발주서와의 일대다 관계
//    @OneToMany(mappedBy = "purchaseRequest", orphanRemoval = true)
//    @Builder.Default
//    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    // 사원(담당자) - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    // 창고_id - 입고될 창고
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse receivingWarehouse;

    // 통화_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    // 발주 요청 일자
    @Column(nullable = false)
    private LocalDate date;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 비고
    @Column(nullable = true)
    private String remarks;

    // 진행 상태
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State status = State.PURCHASE_COMPLETED;

    // 발주 요청 상세 항목 추가 메서드
    public void addPurchaseRequestDetail(PurchaseRequestDetail detail) {
        purchaseRequestDetails.add(detail);
        detail.setPurchaseRequest(this);  // 양방향 관계 설정
    }

    // 발주 요청 상세 항목 삭제 메서드
    public void removePurchaseRequestDetail(PurchaseRequestDetail detail) {
        purchaseRequestDetails.remove(detail);
        detail.setPurchaseRequest(null);
    }
}
