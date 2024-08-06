package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration;

import jakarta.persistence.*;

public class WarehouseHierarchyGroup {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Id
    @ManyToOne
    @JoinColumn(name = "hierarchyGroup_id")
    private HierarchyGroup hierarchyGroup;
}