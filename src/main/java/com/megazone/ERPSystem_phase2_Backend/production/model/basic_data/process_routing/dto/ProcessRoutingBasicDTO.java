package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessRoutingBasicDTO {
    private Long id;
    private String code;
    private String name;
}

