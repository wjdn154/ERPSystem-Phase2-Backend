package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;


import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDataUpdateDTO {

    private Long id;
    private String equipmentNum;                //설비번호
    private String equipmentName;               //설비명
    private EquipmentType equipmentType;        //설비 유형. 조립, 가공, 포장, 검사, 물류
    private String manufacturer;                //제조사.
    private String modelName;                 //동일한 모델명을 가진 여러 설비가 있을 수 있음.
    private LocalDate purchaseDate;             //설비 구매날짜
    private LocalDate installDate;              //설비 설치날짜
    private OperationStatus operationStatus;    //설비 상태 (가동중/유지보수중/고장/수리중)
    private BigDecimal cost;                    //설비 구매 비용
    private String workcenterCode;                //설비가 설치된 위치 or 구역 (작업장). 작업장 테이블 작업장 코드 참조
    private String factoryCode;               //설비가 설치된 공장 . 창고 테이블에 있는 공장코드 참조
    private String equipmentImg;                //설비 이미지
    private Long companyId;


}

