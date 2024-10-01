package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    // 근태 등록
    @PostMapping("/check-in")
    public ResponseEntity<String> recordAttendance(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam(value = "checkInTime", required = false) String checkInTimeStr,
            @RequestParam(value = "checkOutTime", required = false) String checkOutTimeStr,
            @RequestParam(value = "status", defaultValue = "AUTO") String selectedStatus) {

        // checkInTime이 빈 문자열 또는 null이면 null로 처리
        LocalTime checkInTime = (checkInTimeStr != null && !checkInTimeStr.trim().isEmpty()) ? LocalTime.parse(checkInTimeStr) : null;

        // checkOutTime이 빈 문자열 또는 null이면 null로 처리
        LocalTime checkOutTime = (checkOutTimeStr != null && !checkOutTimeStr.trim().isEmpty()) ? LocalTime.parse(checkOutTimeStr) : null;

        LocalDate date = LocalDate.now();

        // 근태 상태 자동 판단
        String attendanceStatus = attendanceService.determineAttendanceStatus(checkInTime, checkOutTime, selectedStatus);

        // 근태 기록 저장
        attendanceService.saveAttendance(employeeId, date, checkInTime, checkOutTime, attendanceStatus);

        // 응답 반환
        return ResponseEntity.ok("근태 상태: " + attendanceStatus);
    }

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
    @PostMapping("/del")
    public ResponseEntity<String> deleteAttendanceRecord(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)

    {
        boolean isDeleted = attendanceService.deleteAttendanceRecord(employeeId, date);
        if (isDeleted) {
            return ResponseEntity.ok("근태 기록이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(404).body("해당 근태 기록을 찾을 수 없습니다.");
        }
    }
}
