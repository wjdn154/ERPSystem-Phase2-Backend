package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 발주서 테이블
 * 발주서에 대한 정보가 있는 테이블
 */
@Entity
@Data
@ToString(exclude = "purchaseOrderDetails")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 발주서 상세와의 일대다 관계
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();

    // 발주 요청 - N : 1
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "purchase_request_id", nullable = true)
//    private PurchaseRequest purchaseRequest;

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

    // 통화_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    // 부가세_id
    @Column(nullable = false)
    private Long vatId;

    // 분개유형_code
    @Column(nullable = false)
    private String journalEntryCode;

    // 세금계산서발행여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ElectronicTaxInvoiceStatus electronicTaxInvoiceStatus = ElectronicTaxInvoiceStatus.UNPUBLISHED;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;

    // 진행 상태
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State status = State.PURCHASE_COMPLETED;

    // 발주서 상세 항목 추가 메서드
    public void addPurchaseOrderDetail(PurchaseOrderDetail detail) {
        if (detail != null) {
            purchaseOrderDetails.add(detail);
            detail.setPurchaseOrder(this); // 양방향 관계 설정
        }
    }
}
