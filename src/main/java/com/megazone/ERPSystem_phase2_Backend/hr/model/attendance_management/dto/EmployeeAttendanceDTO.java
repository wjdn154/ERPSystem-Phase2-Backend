package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 특정 사원의 출퇴근 기록
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendanceDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String employeeNumber;
    private String attendanceCode;
    private Long positionId;
    private String positionName;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private AttendanceStatus status;

    public static EmployeeAttendanceDTO create(Attendance attendance) {
        return new EmployeeAttendanceDTO(
                attendance.getId(),
                attendance.getEmployee().getId(),
                attendance.getEmployee().getLastName()+attendance.getEmployee().getFirstName(),
                attendance.getEmployee().getEmployeeNumber(),
                attendance.getAttendanceCode(),
                attendance.getEmployee().getPosition().getId(),
                attendance.getEmployee().getPosition().getPositionName(),
                attendance.getDate(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getStatus()
        );
    }

}
