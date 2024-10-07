package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 보내는 재고: 기존 재고를 의미하며, 별도의 sendInventory 필드가 필요하지 않음.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;  // 기존 재고 정보

    // 받는 재고와의 연관관계 (새롭게 생성되는 재고)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "receive_inventory_id", nullable = true)
    private Inventory receiveInventory;  // 받는 재고 (새로운 재고)

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;  // 창고 정보

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "send_warehouse_location_id", nullable = false)
    private WarehouseLocation sendWarehouseLocation;  // 보내는 위치 정보

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "receive_warehouse_location_id", nullable = true)
    private WarehouseLocation receiveWarehouseLocation;  // 받는 위치 정보

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // 작업자 정보

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;  // 작업 일자

    @Column(name = "slip_date", nullable = false)
    private LocalDate slipDate;  // 전표 일자

    @Column(name = "work_number", nullable = false)
    private Long workNumber;  // 작업 번호

    @Column(name = "slip_number", nullable = false)
    private Long slipNumber;  // 전표 번호

    private String workType;  // 작업 유형

    private Long receiveInventoryNumber;  // 이동 후 재고 번호

    private Long workQuantity;  // 작업 수량

    private String summary;  // 비고
}
