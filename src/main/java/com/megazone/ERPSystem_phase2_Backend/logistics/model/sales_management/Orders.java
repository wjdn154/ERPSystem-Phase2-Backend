package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.Currency;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문서 테이블
 * 주문서에 대한 정보가 있는 테이블
 */
@Entity
@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문서 상세
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<OrdersDetail> ordersDetails = new ArrayList<>();

    // 거래처 - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 사원(담당자) - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    // 창고_id - 출하 창고
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse shippingWarehouse;

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

    // 일자
    @Column(nullable = false)
    private LocalDate date;

    // 납기 일자
    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SaleState state = SaleState.IN_PROGRESS;

    // 비고
    @Column
    private String remarks;

    public void addOrdersDetail(OrdersDetail ordersDetail) {
        this.ordersDetails.add(ordersDetail);
        ordersDetail.setOrders(this);
    }

}
