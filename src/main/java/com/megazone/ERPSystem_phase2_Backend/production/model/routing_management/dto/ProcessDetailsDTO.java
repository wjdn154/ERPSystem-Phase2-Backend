package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessDetailsDTO {
    private Long id;
    private String code;
    private String name;
    private Boolean isOutsourced;
    private Double duration;
    private BigDecimal cost;
    private Double defectRate;
    private String description;
    private Boolean isUsed;
    private List<WorkcenterDTO> workcenterDTOList = new ArrayList<>(); // 연관 작업장 목록
    private List<RoutingStepDTO> routingStepDTOList = new ArrayList<>(); // 연관 RoutingStep 목록 (필요 시)
}
