package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionProcessDTO {
    private Long id;
    private String code;
    private String name;

    public ProductionProcessDTO(ProcessDetails processDetails) {
        this.id = processDetails.getId();
        this.code = processDetails.getCode();
        this.name = processDetails.getName();
    }
}
