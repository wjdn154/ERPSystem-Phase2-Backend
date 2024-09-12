package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendanceDTO {
    private Long employeeId;
    private String attendanceCode;
    private LocalDate date;
    private Time checkTime;
    private Time checkoutTIme;
    private AttendanceStatus status;

}
