package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private List<WorkcenterDTO> workcenterDTOList; // 연관 작업장 목록
    private List<RoutingStepId> routingStepIdList; // 이 공정을 사용하는 RoutingStep의 ID 목록

}
