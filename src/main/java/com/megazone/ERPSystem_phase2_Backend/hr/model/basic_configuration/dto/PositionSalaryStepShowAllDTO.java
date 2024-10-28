package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionSalaryStepShowAllDTO {
    private List<PositionSalaryStepShowDTO> positionSalaryStepShowDTOList;
    private List<PositionSalaryStepDateDetailDTO> positionSalaryStepDateDetailDTOList;

    public static PositionSalaryStepShowAllDTO create(List<PositionSalaryStepShowDTO> showDTOList, List<PositionSalaryStepDateDetailDTO> dateList) {
        return new PositionSalaryStepShowAllDTO(showDTOList, dateList);
    }
}
