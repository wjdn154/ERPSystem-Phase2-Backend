package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Attendance.AttendanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

//    @Override
//    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
//        return attendanceRepository.findByEmployee_Id(employeeId);
//    }

    @Override
    public List<EmployeeAttendanceDTO> getAttendanceRecords(Long employeeId) {
        List<Attendance> attendanceRecords = attendanceRepository.findByEmployee_Id(employeeId);

        // Attendance를 EmployeeAttendanceDTO로 변환
        return attendanceRecords.stream()
                .map(attendance -> new EmployeeAttendanceDTO(
                        attendance.getEmployee().getId(),
                        attendance.getAttendanceCode(),
                        attendance.getDate(),
                        attendance.getCheckTime(),
                        attendance.getCheckoutTime(),
                        attendance.getStatus()
                ))
                .collect(Collectors.toList());
    }
    }

