package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 출하지시서 테이블
 * 출하지시서에 대한 정보가 있는 테이블
 * 고객이 주문한 상품의 배송을 안내하는 지시서 - 물류쪽에 출하지시를 내림
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrder {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출하지시서 상세와의 일대다 관계
    @OneToMany(mappedBy = "shippingOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ShippingOrderDetail> shippingOrderDetails = new ArrayList<>();

    // 거래처 - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 사원(담당자) - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    // 출하 창고_id - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse shippingWarehouse;

    // 주소 - 배송 보낼 주소(거래처 주소가 될 수도 있음)
    @Column
    private String shippingAddress;

    // 우편번호 - 거래처에 우편번호가 될 수도 있음(직접 입력도 가능)
    @Column
    private String postalCode;

    // 출하예정일자
    @Column(nullable = false)
    private LocalDate shippingDate;

    // 일자 - 출하지시서 입력 일자
    @Column(nullable = false)
    private LocalDate date;

    // 비고
    @Column
    private String remarks;

    // 진행상태 (진행중, 완료)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SaleState state = SaleState.WAITING_FOR_SHIPMENT;

    public void addShippingOrderDetail(ShippingOrderDetail shippingOrderDetail) {
        this.shippingOrderDetails.add(shippingOrderDetail);
        shippingOrderDetail.setShippingOrder(this);
    }
}
