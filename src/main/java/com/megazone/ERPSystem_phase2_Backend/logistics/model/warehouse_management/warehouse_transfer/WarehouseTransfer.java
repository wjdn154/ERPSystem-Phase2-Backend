package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sending_warehouse_id", nullable = false)
    private Warehouse sendingWarehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiving_warehouse_id", nullable = false)
    private Warehouse receivingWarehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "warehouseTransfer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WarehouseTransferProduct> warehouseTransferProducts;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column(name = "transfer_number", nullable = false)
    private Long transferNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransferStatus status; // ENUM('미확인', '확인')

    @Column(name = "comment")
    private String comment; // 이동과 관련된 비고사항 (nullable)

}
