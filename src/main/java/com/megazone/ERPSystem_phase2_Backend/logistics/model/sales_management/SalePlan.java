package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 판매계획 테이블
 * 판매서에 대한 정보가 있는 테이블
 * 등록된 주문을 바탕으로 실제 판매를 등록
 */
@Entity
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalePlan {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매 계획 상세
    @OneToMany(mappedBy = "salePlan", orphanRemoval = true)
    @Builder.Default
    private List<SalePlanDetail> salePlanDetails = new ArrayList<>();

    // 거래처
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 사원
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    // 창고
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column
    private LocalDate date;

    @Column
    private LocalDate ExpectedSalesDate;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SaleState state = SaleState.IN_PROGRESS;

    // 비고
    @Column
    private String remarks;
}
