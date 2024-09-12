package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/attendance")
public class AttendanceController {
    @Autowired
    private final AttendanceService attendanceService;
    @Autowired
    private UsersRepository usersRepository;

    // 특정 사원의 출퇴근 기록 조회
    @PostMapping("/records/{employeeId}")
    public ResponseEntity<List<EmployeeAttendanceDTO>> getAttendanceRecords(@PathVariable Long employeeId){
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long company_id = user.getCompany().getId();
        List<EmployeeAttendanceDTO> attendanceRecords = attendanceService.getAttendanceRecords(employeeId);
        return ResponseEntity.ok(attendanceRecords);
    }

}
