package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "warehouse_hierarchy_group")
@Table(name = "warehouse_hierarchy_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
