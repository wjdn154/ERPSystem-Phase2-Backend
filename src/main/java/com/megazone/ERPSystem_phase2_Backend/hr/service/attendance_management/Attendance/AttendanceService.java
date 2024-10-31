package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    //List<Attendance> getAttendanceByEmployeeId(Long employeeId);

    Optional<EmployeeAttendanceDTO> getAttendanceRecords(Long id);

    List<AttendanceShowDTO> getAllAttendanceRecords();

    boolean deleteAttendanceRecord(Long employeeId, LocalDate date);

    String saveAttendance(AttendanceEntryDTO dto);

    String determineAttendanceStatus(AttendanceEntryDTO dto);

    boolean updateAttendance(Long employeeId, LocalDate date, AttendanceUpdateDTO dto);
}
