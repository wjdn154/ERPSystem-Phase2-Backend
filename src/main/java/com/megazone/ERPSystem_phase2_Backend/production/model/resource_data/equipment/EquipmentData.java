package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.SerialNo;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

//장비

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                  //pk

    @Column(nullable = false)
    private String equipmentName;     //설비명

    @Column(nullable = false)
    private String equipmentType;     //설비 유형(설비의 종류 .ex?)

    @Column(nullable = false)
    private String manufacturer;     //제조사

    @Column(nullable = false)
    private String modelNumber;     //모델명

    @Column(nullable = false)
    private LocalDate purchaseDate;       //설비 구매날짜

    @Column
    private LocalDate installDate;      //설비 설치날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationStatus operationStatus;     //설비 상태 (가동중/유지보수중/고장/수리중)

    @Column(nullable = false)
    private Long cost;             //설비 구매 비용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id")
    private Workcenter location;       //설비가 설치된 위치 or 구역 (작업장). 작업장 테이블 참조


}
