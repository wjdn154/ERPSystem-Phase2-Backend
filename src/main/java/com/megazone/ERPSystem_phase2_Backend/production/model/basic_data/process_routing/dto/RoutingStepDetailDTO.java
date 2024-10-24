package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutingStepDetailDTO {
    private RoutingStepId id;
    private Long stepOrder;
    private ProcessRouting processRouting;
    private ProcessDetailsDTO processDetails;
}