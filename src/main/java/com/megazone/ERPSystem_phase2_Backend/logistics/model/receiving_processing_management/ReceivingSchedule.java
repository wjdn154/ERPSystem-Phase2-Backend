package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.enums.ReceivingStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingSchedule {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 입고 지시서 상세와의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiving_order_detail_id", nullable = false)
    private ReceivingOrderDetail receivingOrderDetail;

    // 입고될 창고 위치
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_location_id", nullable = false)
    private WarehouseLocation warehouseLocation;

    private String productName;

    // 입고 대기 재고 번호
    @Column(nullable = false)
    private Long pendingInventoryNumber;

    // 입고 대기 수량
    @Column(nullable = false)
    private Integer pendingQuantity;

    // 입고 일자
    @Column(nullable = false)
    private LocalDate receivingDate;

    @Column(nullable = false)
    private Long receivingDateNumber;

    // 입고 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReceivingStatus status = ReceivingStatus.WAITING;

}