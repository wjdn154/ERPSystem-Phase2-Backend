package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceShowDTO {
    private Long id;
    private Long employeeId;
    private String employeeNumber;
    private String employeeName;
    private String positionName;
    private String attendanceCode;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private AttendanceStatus status;

    public static AttendanceShowDTO create(Attendance attendance) {
          return new AttendanceShowDTO(
              attendance.getId(),
              attendance.getEmployee().getId(),
              attendance.getEmployee().getEmployeeNumber(),
              attendance.getEmployee().getLastName()+ attendance.getEmployee().getFirstName(),
              attendance.getEmployee().getPosition().getPositionName(),
              attendance.getAttendanceCode(),
              attendance.getDate(),
              attendance.getCheckInTime(),
              attendance.getCheckOutTime(),
              attendance.getStatus()
          );
    }
}
