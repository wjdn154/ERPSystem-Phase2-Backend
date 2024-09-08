package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaintenanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListMaintenanceHistoryDTO {

    private Long id;                              //pk
    private String equipmentDataName;             //설비명
    private String maintenanceManager;            //담당자
    private MaintenanceType maintenanceType;      //유지보수 유형
    private Boolean maintenanceStatus;            //유지보수 진행 상태 (진행중/완료)
    private LocalDate maintenanceDate;            //유지보수 일자
    private String workcenterName;                //설비엔티티의 작업장 이름
    private String factoryName;                   //설비엔티티의 공장 이름

}
