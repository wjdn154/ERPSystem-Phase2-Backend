package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "HierarchyGroup")
@Table(name = "hierarchy_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "hierarchy_group_code", nullable = false)
    private String hierarchyGroupCode;

    @Column(name = "hierarchy_group_name", nullable = false)
    private String hierarchyGroupName;

    @OneToMany(mappedBy = "hierarchyGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WarehouseHierarchyGroup> warehouseHierarchyGroups = new ArrayList<>();
}
