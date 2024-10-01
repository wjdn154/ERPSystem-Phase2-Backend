package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceShowDTO {
    private Long attendanceId;
    private String employeeNumber;
    private String employeeName;
    private String titleName;
    private String attendanceCode;
    private LocalDate date;
    private Time checkInTime;
    private Time checkOutTime;
    private AttendanceStatus status;

    public static AttendanceShowDTO create(Attendance attendance) {
          return new AttendanceShowDTO(
              attendance.getId(),
              attendance.getEmployee().getEmployeeNumber(),
              attendance.getEmployee().getLastName()+ attendance.getEmployee().getFirstName(),
              attendance.getEmployee().getJobTitle().getTitleName(),
              attendance.getAttendanceCode(),
              attendance.getDate(),
              attendance.getCheckinTime(),
              attendance.getCheckoutTime(),
              attendance.getStatus()
          );
    }
}
