package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "hierarchy_group_code", nullable = false)
    private Long hierarchyGroupCode;

    @Column(name = "hierarchy_group_code", nullable = false)
    private String hierarchyGroupName;
}