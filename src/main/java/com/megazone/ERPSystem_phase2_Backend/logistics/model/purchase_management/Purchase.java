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
 * 구매서 테이블
 * 구매서에 대한 정보가 있는 테이블
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    // 구매서와 발주서 간의 일대일 관계
//    @OneToOne
//    @JoinColumn(name = "purchase_order_id")
//    private PurchaseOrder purchaseOrder;
//
//    // 주문서_id
//    @Column
//    private Long ordersId;

    // 구매서 상세와의 일대다 관계
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PurchaseDetail> purchaseDetails = new ArrayList<>();

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
    private String vatId;

    // 분개유형_code
    @Column(nullable = false)
    private String journalEntryCode;

    // 세금계산서발행여부
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ElectronicTaxInvoiceStatus electronicTaxInvoiceStatus = ElectronicTaxInvoiceStatus.UNPUBLISHED;

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
    private State status = State.INVOICED;

    // 회계 반영 여부
    @Column
    @Builder.Default
    private Boolean accountingReflection = false;

    // 구매 전표와 일대일 관계 설정
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "invoice_id", nullable = true)
    private PurchaseInvoice purchaseInvoice;

    public void addPurchaseDetail(PurchaseDetail purchaseDetail) {
        this.purchaseDetails.add(purchaseDetail);
        purchaseDetail.setPurchase(this);
    }
}
