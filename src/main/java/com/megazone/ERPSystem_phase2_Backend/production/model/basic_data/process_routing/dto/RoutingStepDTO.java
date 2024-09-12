package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutingStepDTO {
    private RoutingStepId id;
    private Long routingId;
    private Long processId;
    private Long stepOrder; // 공정 순서 등 추가 정보 포함 가능
    private ProcessDetailsDTO processDetailsDTO; // 해당 RoutingStep이 속한 공정 정보
    private ProcessRoutingDTO processRoutingDTO; // 해당 RoutingStep이 속한 Routing 정보

}
