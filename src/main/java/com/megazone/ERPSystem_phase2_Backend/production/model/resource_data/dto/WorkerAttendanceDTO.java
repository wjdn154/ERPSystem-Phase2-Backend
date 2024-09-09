package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class WorkerAttendanceDTO {

    private Long id;
    private String workcenterCode;       //작업배치의 작업장 코드
    private String workcenterName;       //작업배치의 작업장 이름
    private String assignmentDate;       //작업배치의 작업배치 날짜
    private String attendanceCode;       //인사의 근태의 근태코드
    private LocalDate attendanceDate;    //인사의 근태의 출근날짜
    private Time checkTime;              //인사의 근태의 출근시간
    private Time checkoutTime;           //인사의 근태의 퇴근시간
    private AttendanceStatus attendanceStatus;    //인사의 근태의 근무 상태
}
