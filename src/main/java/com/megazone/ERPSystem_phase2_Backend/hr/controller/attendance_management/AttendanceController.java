package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    // 특정 사원의 출퇴근 기록 조회
    @PostMapping("/records/{employeeId}")
    public ResponseEntity<List<EmployeeAttendanceDTO>> getAttendanceRecords(@PathVariable Long employeeId){
        List<EmployeeAttendanceDTO> attendanceRecords = attendanceService.getAttendanceRecords(employeeId);
        return ResponseEntity.ok(attendanceRecords);
    }

    // 모든 사원의 출퇴근 기록 조회
    @PostMapping("/records/all")
    public ResponseEntity<List<AttendanceShowDTO>> getAllAttendanceRecords(){
        List<AttendanceShowDTO> attendanceshowRecords = attendanceService.getAllAttendanceRecords();
        return ResponseEntity.ok(attendanceshowRecords);
    }

    // 근태 삭제
    @PostMapping("/del/{employeeId}")
    public ResponseEntity<String> deleteAttendanceRecord(@PathVariable Long employeeId){
        boolean isDeleted = attendanceService.deleteAttendanceRecord(employeeId);
        if(isDeleted){
            return ResponseEntity.ok("근태 기록이 삭제되었습니다.");
        } else{
            return ResponseEntity.status(404).body("사원에 맞는 근태 기록이 없습니다.");
        }
    }

    // 사원 근태 수정
//    @PostMapping("/records/update/{employeeId}")
//    public ResponseEntity
}
