package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftTypeDTO {
    private Long id;
    private String name;       // 교대 근무 이름
    private String description; // 교대 근무 설명
    private Double duration;     // 근무 시간 (시간 단위)
    private Boolean used; // 사용여부


}
