package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto;

public class WorkerDetailDTO {

    private String trainingStatue;     //안전교육 이수 여부(이수/미이수)
    private String employeeNumber;     //사원 번호
    private String employeeFirstName;  //이름
    private String employeeLastName;   //성
    private String departmentName;     //employee 의 department 의 departmentName 이 생산인 것만 참조
    private String positionName;       //employee 의 position 의 positionName 참조
    private String jobTitleName;       //employee 의 jobTitle 의 titleName 참조
    private String attendanceCode;     //employee 의 attendance 의 근태코드
    private String attendanceDate;     //employee 의 attendance 의 날짜
    private String checkTime;          //employee 의 attendance 의 출근시간
    private String checkoutTime;       //employee 의 attendance 의 퇴근시간
    private String status;             //employee 의 attendance 의 근무상태 (출근/지각/조퇴/결근)
    private String phoneNumber;        //전화번호0
    private String employmentStatus;   //고용상태(재직중(ACTIVE), 휴직중(ON_LEAVE), 퇴직(RESIGNED))
    private String employmentType;     //고용유형(정규직, 계약직, 파트타임, 임시직, 인턴, 일용직,프리랜서) EmploymentType 참조
    private String hireDate;           //고용일
    private String profilePicture;     //프로필 사진

}


