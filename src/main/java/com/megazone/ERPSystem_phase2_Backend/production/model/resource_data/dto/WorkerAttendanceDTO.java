package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class WorkerAttendanceDTO {


    private String attendanceCode;     //employee 의 attendance 의 근태코드
    private String attendanceDate;     //employee 의 attendance 의 날짜
    private String checkTime;          //employee 의 attendance 의 출근시간
    private String checkoutTime;       //employee 의 attendance 의 퇴근시간
    private String status;             //employee 의 attendance 의 근무상태 (출근/지각/조퇴/결근)
}
