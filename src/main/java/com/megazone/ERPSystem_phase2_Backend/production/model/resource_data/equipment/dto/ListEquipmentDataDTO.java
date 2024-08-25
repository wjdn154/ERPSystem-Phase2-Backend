package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ListEquipmentDataDTO {

    private Long id;
    private String equipmentNum;          //설비 번호
    private String equipmentName;         //설비 명
    private String modelName;             //모델 명
    private EquipmentType equipmentType;  //설비 유형
    private OperationStatus operationStatus;    //가동 상태
    private String factoryName;           //공장이름
    private String workcenterName;        //작업장 이름


}
