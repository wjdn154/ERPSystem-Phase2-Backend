package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDTO {
    private Long id;
    private String address;
    private String warehouseType;
    private String warehouseHierarchyGroup;
    private String code;
    private String name;
    private String productionProcess;
    private Boolean isActive;
}
