package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    // 근태 등록
    @PostMapping("/check-in")
    public ResponseEntity<String> recordAttendance(@RequestBody AttendanceEntryDTO dto){
        try {
            // 근태 기록 저장
            String result = attendanceService.saveAttendance(dto);
            return ResponseEntity.status(HttpStatus.OK).body("근태 상태: " + result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("근태등록 실패 사유 : " + e.getMessage());
        }

    }

    // 특정 사원의 출퇴근 기록 조회
    @PostMapping("/records/{id}")
    public ResponseEntity<EmployeeAttendanceDTO> getAttendanceRecords(@PathVariable Long id){
        Optional<EmployeeAttendanceDTO> attendanceRecords = attendanceService.getAttendanceRecords(id);
        return attendanceRecords.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 모든 사원의 출퇴근 기록 조회
    @PostMapping("/records/all")
    public ResponseEntity<List<AttendanceShowDTO>> getAllAttendanceRecords(){
        List<AttendanceShowDTO> attendanceShowRecords = attendanceService.getAllAttendanceRecords();
        return ResponseEntity.ok(attendanceShowRecords);
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

    // 근태 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateAttendance(
            @RequestBody AttendanceUpdateDTO dto) // DTO를 통한 데이터 전달
    {
        try {
            // 근태 기록 수정
            boolean isUpdated = attendanceService.updateAttendance(dto.getEmployeeId(), dto.getDate(), dto);
            if (isUpdated) {
                return ResponseEntity.ok("근태 기록이 수정되었습니다.");
            } else {
                return ResponseEntity.status(404).body("해당 근태 기록을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("근태 수정 실패 사유 : " + e.getMessage());
        }
    }
}
