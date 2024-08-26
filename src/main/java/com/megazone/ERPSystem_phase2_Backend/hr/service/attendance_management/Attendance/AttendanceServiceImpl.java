//package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;
//
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
//import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Attendance.AttendanceRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AttendanceServiceImpl implements AttendanceService {
//    private final AttendanceRepository attendanceRepository;
//
//    @Override
//    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
//        return attendanceRepository.findByEmployeeId(employeeId);
//    }
//}
