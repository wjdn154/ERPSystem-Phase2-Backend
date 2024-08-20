package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;


import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDataDTO {

    private Long id;                            //pk
    private String equipmentNum;                //설비번호
    private String equipmentName;               //설비명
    private EquipmentType equipmentType;        //설비 유형. 조립, 가공, 포장, 검사, 물류
    private String manufacturer;                //제조사.
    private String modelNumber;                 //동일한 모델명을 가진 여러 설비가 있을 수 있음.
    private LocalDate purchaseDate;             //설비 구매날짜
    private LocalDate installDate;              //설비 설치날짜
    private OperationStatus operationStatus;    //설비 상태 (가동중/유지보수중/고장/수리중)
    private BigDecimal cost;                    //설비 구매 비용
    private Workcenter location;                //설비가 설치된 위치 or 구역 (작업장). 작업장 테이블 참조
    private String factory;                     //설비가 설치된 공장 . 공장 테이블 참조?
    private String equipmentImg;                //설비 이미지


    public EquipmentData toEntity(EquipmentDataDTO dto) {
        return null;
    }
}

