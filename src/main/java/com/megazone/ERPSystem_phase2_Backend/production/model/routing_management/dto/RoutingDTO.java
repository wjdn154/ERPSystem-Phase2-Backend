package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutingDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean isStandard;
    private boolean isActive;
    private List<RoutingStepDTO> routingStepDTOList; // 연관 RoutingStep 목록
}
