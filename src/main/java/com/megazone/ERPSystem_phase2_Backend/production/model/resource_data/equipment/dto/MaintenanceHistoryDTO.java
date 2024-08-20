package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaintenanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceHistoryDTO {


    private LocalDate date;                       //유지보수 일자
    private String maintenanceManager;            //담당자. 타입 작업자?workerDTO?
    private EquipmentDataDTO equipmentDataDTO;    //설비명
    private String workcenter;                    //작업장. 타입 WorkcenterDTO?
    private MaintenanceType maintenanceType;      //유지보수 유형
    private String title;                         //유지보수 제목
    private String maintenanceDetail;             //유지보수 내용
    private Boolean maintenanceStatus;            //유지보수 진행 상태 (진행중/완료)
    private BigDecimal maintenanceCost;           //유지보수에 드는 비용
}
