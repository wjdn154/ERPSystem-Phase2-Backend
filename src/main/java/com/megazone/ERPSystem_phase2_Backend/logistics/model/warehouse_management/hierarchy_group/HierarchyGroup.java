package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "hierarchy_group")
@Table(name = "hierarchy_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HierarchyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "hierarchy_group_code", nullable = false, unique = true)
    private String hierarchyGroupCode;

    @Column(name = "hierarchy_group_name", nullable = false)
    private String hierarchyGroupName;

    private Boolean isActive;

    @OneToMany(mappedBy = "hierarchyGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default

    private List<WarehouseHierarchyGroup> warehouseHierarchyGroups = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_group_id")
    private HierarchyGroup parentGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HierarchyGroup> childGroup = new ArrayList<>();
}
