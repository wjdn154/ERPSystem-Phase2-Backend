package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class WorkerDetailShowDTO {

    private Long id;
    private Boolean trainingStatue;     //안전교육 이수 여부(이수/미이수)
    private String employeeNumber;     //employee 의 사원 번호
    private String employeeFirstName;  //employee 의 이름
    private String employeeLastName;   //employee 의 성
    private String departmentName;     //employee 의 department 의 departmentName 이 생산인 것만 참조
    private String positionName;       //employee 의 position 의 positionName 참조
    private String jobTitleName;       //employee 의 jobTitle 의 titleName 참조

    private String phoneNumber;        //전화번호
    private EmploymentStatus employmentStatus;   //고용상태(재직중(ACTIVE), 휴직중(ON_LEAVE), 퇴직(RESIGNED))
    private EmploymentType employmentType;       //고용유형(정규직, 계약직, 파트타임, 임시직, 인턴, 일용직,프리랜서) EmploymentType 참조
    private LocalDate hireDate;                  //고용일
    private String profilePicture;               //프로필 사진
    private String workerAssignmentId;           //작업배치 아이디
    private String workcenterCode;               //작업장 코드
    private String workcenterName;               //작업장 이름

}


