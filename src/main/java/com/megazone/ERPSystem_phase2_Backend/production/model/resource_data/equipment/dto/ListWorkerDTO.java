package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

public class ListWorkerDTO {

    private String trainingStatue;
    private String employeeNumber;      //사원 번호
    private String employeeFirstName;   //이름
    private String employeeLastName;   //성
    private String departmentName;     //employee의 department의 departmentName이 생산인 것만 참조
    private String positionName;       //employee의 positon의 positionName 참조
    private String jobTitle;           //employee의 jobTitle의 titleName 참조

}


