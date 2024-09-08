package com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.enums.ReceivingStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 *
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receiving {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receivingOrder_id", nullable = false)
    private ReceivingOrder receivingOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "received_date", nullable = false)
    private LocalDateTime receivedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceivingStatus receivingStatus;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "comments", length = 255)
    private String comments;

    @Column(name = "created_at", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private LocalDateTime updatedAt;
}
