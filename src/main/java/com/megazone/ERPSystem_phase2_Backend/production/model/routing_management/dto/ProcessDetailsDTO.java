package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
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
    private String code;
    private String name;
    private Boolean isOutsourced;
    private Double duration; // 표준소요시간
    private BigDecimal cost; // 공정수행비용
    private Boolean isUsed; // 사용 여부

    private List<WorkcenterDTO> workcenterDTOList;

}
