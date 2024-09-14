package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ListWorkerDTO {

    private Long id;
    private String trainingStatus;     //안전교육 이수 여부(이수/미이수)
    private String employeeNumber;     //사원 번호
    private String employeeFirstName;  //이름
    private String employeeLastName;   //성
    private String departmentName;     //employee 의 department 의 departmentName 이 생산인 것만 참조
    private String positionName;       //employee 의 position 의 positionName 참조
    private String jobTitleName;       //employee 의 jobTitle 의 titleName 참조
    private String employmentStatus;   //고용상태(재직중(ACTIVE), 휴직중(ON_LEAVE), 퇴직(RESIGNED))
    private String employmentType;     //고용유형(정규직, 계약직, 파트타임, 임시직, 인턴, 일용직,프리랜서) EmploymentType 참조
}


