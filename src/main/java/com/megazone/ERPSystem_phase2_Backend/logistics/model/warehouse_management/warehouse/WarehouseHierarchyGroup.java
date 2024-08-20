package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "warehouse_hierarchy_group")
@Table(name = "warehouse_hierarchy_group")
@Data
public class WarehouseHierarchyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hierarchy_group_id")
    private HierarchyGroup hierarchyGroup;
}
