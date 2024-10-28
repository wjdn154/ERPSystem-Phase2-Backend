package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 입고 지시서 테이블
 * 입고 지시서에 대한 정보가 있는 테이블
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 입고지시서 상세와의 일대다 관계
    @OneToMany(mappedBy = "receivingOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReceivingOrderDetail> receivingOrderDetails = new ArrayList<>();

    // 거래처 - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 사원(담당자) - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    // 창고_id - 입고될 창고
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse receivingWarehouse;

    // 입고 예정 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 일자 - 입고지시서 입력 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;

    // 진행 상태
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State status = State.WAITING_FOR_RECEIPT;

    public void addReceivingOrderDetail(ReceivingOrderDetail receivingOrderDetail) {
        this.receivingOrderDetails.add(receivingOrderDetail);
        receivingOrderDetail.setReceivingOrder(this);
    }
}
