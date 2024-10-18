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
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeNumber;
    private String attendanceCode;
    private LocalDate date;
    private LocalDateTime checkTime;
    private LocalDateTime checkoutTIme;
    private AttendanceStatus status;

    public static EmployeeAttendanceDTO create(Attendance attendance) {
        return new EmployeeAttendanceDTO(
                attendance.getEmployee().getId(),
                attendance.getEmployee().getLastName(),
                attendance.getEmployee().getFirstName(),
                attendance.getEmployee().getEmployeeNumber(),
                attendance.getAttendanceCode(),
                attendance.getDate(),
                attendance.getCheckinTime(),
                attendance.getCheckoutTime(),
                attendance.getStatus()
        );
    }

}
