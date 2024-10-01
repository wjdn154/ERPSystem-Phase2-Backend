package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {
    //List<Attendance> getAttendanceByEmployeeId(Long employeeId);

    List<EmployeeAttendanceDTO> getAttendanceRecords(Long employeeId);

    List<AttendanceShowDTO> getAllAttendanceRecords();

    boolean deleteAttendanceRecord(Long employeeId, LocalDate date);

    String saveAttendance(Long employeeId, LocalDate date,LocalTime checkInTime, LocalTime checkOutTime, String selectedStatus);

    String determineAttendanceStatus(LocalTime checkInTime, LocalTime checkOutTime, String selectedStatus);
}
