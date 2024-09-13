package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceShowDTO {
    private Long employeeId;
    private String attendanceCode;
    private LocalDate date;
    private Time checkInTime;
    private Time checkOutTime;
    private String status;
}
