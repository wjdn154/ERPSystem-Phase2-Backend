package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "hierarchy_group")
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

    private Boolean isActive;

    @OneToMany(mappedBy = "hierarchyGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WarehouseHierarchyGroup> warehouseHierarchyGroups = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_group_id", referencedColumnName = "id")
    private HierarchyGroup parentGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HierarchyGroup> childGroup = new ArrayList<>();
}
