package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;


import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.EmployeeAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.AttendanceStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Attendance.AttendanceRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    private final LocalDateTime LATE_CHECK_IN_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10));      // 지각 기준 출근 시간
    private final LocalDateTime EARLY_CHECK_OUT_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 50));   // 조퇴 기준 퇴근 시간

    // 근태 상태 자동 결정
    public String determineAttendanceStatus(AttendanceEntryDTO entryDTO) {

        LocalDateTime checkOutTime = entryDTO.getCheckOutTime();
        LocalDateTime checkInTime = entryDTO.getCheckInTime();
        LocalDate date = LocalDate.now();
        // 사용자가 직접 선택한 상태가 있다면 우선 적용
        if (entryDTO.getStatus() != null && !entryDTO.getStatus().equals("AUTO")) {
            return entryDTO.getStatus(); // 공휴일, 출장, 휴가 등
        }

        // 출근 기록이 없으면 결근으로 처리
        if (checkInTime == null) {
            return "ABSENT";  // 결근
        }

        // 출퇴근 시간 기준으로 자동 판단
        boolean isLate = checkInTime.isAfter(LATE_CHECK_IN_TIME);  // 9:10 이후이면 지각
        boolean isEarlyLeave = checkOutTime != null && checkOutTime.isBefore(EARLY_CHECK_OUT_TIME);  // 17:50 이전에 퇴근하면 조퇴

        if (isLate && isEarlyLeave) {
            return "LATE_AND_EARLY_LEAVE";  // 지각 및 조퇴
        } else if (isLate) {
            return "LATE";  // 지각
        } else if (isEarlyLeave) {
            return "EARLY_LEAVE";  // 조퇴
        } else {
            return "PRESENT";  // 정상 출근
        }
    }
    private AttendanceEntryDTO convertToAttendanceEntryDTO(AttendanceUpdateDTO updateDto) {
        AttendanceEntryDTO entryDto = new AttendanceEntryDTO();
        entryDto.setEmployeeId(updateDto.getEmployeeId());
        entryDto.setCheckInTime(updateDto.getCheckInTime());
        entryDto.setCheckOutTime(updateDto.getCheckOutTime());
        entryDto.setStatus(updateDto.getStatus());
        entryDto.setDate(updateDto.getDate());  // 필요한 필드 추가
        return entryDto;
    }

    @Override
    public boolean updateAttendance(Long employeeId, LocalDate date, AttendanceUpdateDTO dto) {
        // 1. 사원의 근태 기록 조회
        Optional<Attendance> optionalRecord = attendanceRepository.findByEmployeeIdAndDate(employeeId, date);

        // 2. 기록이 존재하지 않으면 false 반환
        if (!optionalRecord.isPresent()) {
            return false;
        }

        // 3. 기록이 존재하면 해당 근태 기록 수정
        Attendance record = optionalRecord.get();

        // Check-in 시간 수정
        if (dto.getCheckInTime() != null) {
            record.setCheckInTime(dto.getCheckInTime());
        }

        // Check-out 시간 수정
        if (dto.getCheckOutTime() != null) {
            record.setCheckOutTime(dto.getCheckOutTime());
        }
        AttendanceEntryDTO entryDto = convertToAttendanceEntryDTO(dto);

        String status = determineAttendanceStatus(entryDto);
        record.setStatus(AttendanceStatus.valueOf(status));

        // 4. 수정된 근태 기록 저장
        attendanceRepository.save(record);

        // 5. 수정이 성공적으로 완료되었음을 나타내기 위해 true 반환
        return true;
    }


    @Override
    public String saveAttendance(AttendanceEntryDTO dto) {
        // 1. 사원 정보 조회
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사원을 찾을 수 없습니다."));

        // 2. 근태 상태 결정
        String attendanceStatus = determineAttendanceStatus(dto);

        // 3. Attendance 엔티티 생성
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);  // 사원 참조 설정
        attendance.setAttendanceCode(generateAttendanceCode(dto.getEmployeeId(),dto.getDate()));
        attendance.setDate(dto.getDate());  // 날짜 설정

        // checkInTime과 checkOutTime이 null이 아닐 때만 Time으로 변환
        attendance.setCheckInTime(dto.getCheckInTime());  // 출근 시간 설정 (null 처리)
        attendance.setCheckOutTime(dto.getCheckOutTime());  // 퇴근 시간 설정 (null 처리)

        attendance.setStatus(AttendanceStatus.valueOf(attendanceStatus));  // 근태 상태 설정

        // 4. Attendance 엔티티 저장
        attendanceRepository.save(attendance);

        // 5. 결정된 근태 상태 반환
        return attendanceStatus;
    }

    private String generateAttendanceCode(Long employeeId, LocalDate date) {
        return "ATD" + employeeId + date.toString().replace("-", "");
    }

    // 특정 사원 근태 기록 조회
    @Override
    public List<EmployeeAttendanceDTO> getAttendanceRecords(Long employeeId) {
        List<Attendance> attendanceRecords = attendanceRepository.findByEmployee_Id(employeeId);

        // Attendance를 EmployeeAttendanceDTO로 변환
        return attendanceRecords.stream()
                .map(EmployeeAttendanceDTO::create)
                .collect(Collectors.toList());
    }

    // 모든 직원 근태 기록 조회
    @Override
    public List<AttendanceShowDTO> getAllAttendanceRecords() {
        List<Attendance> attendanceList = attendanceRepository.findAll();

        // Attendance 엔티티를 AttendanceDTO로 변환
        return attendanceList.stream()
                .map(AttendanceShowDTO::create)
            .collect(Collectors.toList());
    }


    // 근태 기록 삭제
    @Override
    public boolean deleteAttendanceRecord(Long employeeId, LocalDate date) {
        Optional<Attendance> attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, date);

        if (attendance.isPresent()) {
            attendanceRepository.delete(attendance.get());
            return true;
        } else {
            return false;
        }
    }

}

