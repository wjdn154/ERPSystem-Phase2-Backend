package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.SerialNo;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.EquipmentType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.OperationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

//장비

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                              //pk
    
    @Column(nullable = false)
    private String equipmentNum;                 //설비 번호

    @Column(nullable = false)                    //설비명. 특정 설비를 식별할 수 있는 고유한 이름.
    private String equipmentName;                //여러대의 동일한 모델이 있을 경우에도 각각의 설비에 고유한 이름을 부여하여 구분 가능.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;        //설비 유형. 조립, 가공, 포장, 검사, 물류

    @Column(nullable = false)
    private String manufacturer;                //제조사.

    @Column(nullable = false)                   //모델명. 설비의 제조업체가 지정한 모델 번호나 명칭을 의미함.
    private String modelNumber;                 //동일한 모델명을 가진 여러 설비가 있을 수 있음.

    @Column(nullable = false)
    private LocalDate purchaseDate;             //설비 구매날짜

    @Column
    private LocalDate installDate;              //설비 설치날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationStatus operationStatus;     //설비 상태 (가동중/유지보수중/고장/수리중)

    @Column(nullable = false)
    private BigDecimal cost;                    //설비 구매 비용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id")
    private Workcenter workcenter;               //설비가 설치된 위치 or 구역 (작업장). 작업장 테이블 참조

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "factory_id")
//    private Factory factory;                   //설비가 설치된 공장 . 공장 테이블 참조

    @Column
    private String equipmentImg;
}
