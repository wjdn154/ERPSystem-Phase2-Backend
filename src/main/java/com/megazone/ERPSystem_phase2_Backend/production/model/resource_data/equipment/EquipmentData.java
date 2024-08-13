package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

import java.util.Date;

//장비
public class EquipmentData {

    private Long id;                  //pk
 
    private String equipmentName;     //설비명
    
    private String equipmentType;     //설비 유형
    
    private String manufacturer;     //제조사
    
    private String modelNumber;     //모델명
    
    private String serialNumber;    //시리얼 넘버
    
    private Date purchaseDate;       //구매날짜
    
    private Date installDate;      //설치날짜
    
    private String location;       //설비가 설치된 위치 or 구역 (작업장)
    
    private Long cost;             //설비 구매 비용
}
