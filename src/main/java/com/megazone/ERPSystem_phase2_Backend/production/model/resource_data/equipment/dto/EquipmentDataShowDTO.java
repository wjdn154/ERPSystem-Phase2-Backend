package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDataShowDTO {

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
    private String workcenterCode;
    private String workcenterName;                //설비가 설치된 위치 or 구역 (작업장). 작업장 테이블 작업장 코드 참조
    private String factoryCode;
    private String factoryName;               //설비가 설치된 공장 . 창고 테이블에 있는 공장코드 참조
    private String imagePath;                //설비 이미지
    private Long kWh; // 설비의 시간당 전력소비량(kWh)

    private MultipartFile imageFile;            // 실제 이미지 파일

    //private LocalDate maintenanceDate;        //마지막 유지보수 날짜
    //private List<MaintenanceHistoryDetailDTO> maintenanceHistoryList;  //해당 설비 유지보수 이력 리스트
}
