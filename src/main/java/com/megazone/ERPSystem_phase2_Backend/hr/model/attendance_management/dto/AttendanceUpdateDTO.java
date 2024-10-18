package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceUpdateDTO {
    private Long employeeId; // 사원 ID
    private LocalDate date; // 근태 날짜
    private LocalDateTime checkInTime; // 출근 시간
    private LocalDateTime checkOutTime; // 퇴근 시간
    private String status;

    public static AttendanceUpdateDTO update(Attendance attendance) {
        return new AttendanceUpdateDTO(
                attendance.getEmployee().getId(),
                attendance.getDate(),
                attendance.getCheckinTime(),
                attendance.getCheckoutTime(),
                attendance.getStatus() != null ? attendance.getStatus().toString() : "AUTO"
        );
    }
}
