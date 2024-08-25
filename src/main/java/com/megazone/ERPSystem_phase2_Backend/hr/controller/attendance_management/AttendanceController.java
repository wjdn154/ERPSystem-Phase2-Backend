//package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;
//
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
//import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/hr/attendance")
//public class AttendanceController {
//    private final AttendanceService attendanceService;
//
//    @PostMapping("/{employeeId}")
//    public List<Attendance> getAttendanceByEmployeeId(@PathVariable Long employeeId) {
//        return attendanceService.getAttendanceByEmployeeId(employeeId);
//    }
//
//}
