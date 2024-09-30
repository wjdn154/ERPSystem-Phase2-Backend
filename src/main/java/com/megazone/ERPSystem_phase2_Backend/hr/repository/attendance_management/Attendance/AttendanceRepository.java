package com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Attendance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.AttendanceShowDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepositoryCustom{
    List<Attendance> findByEmployee_Id(Long employeeId); //특정 직원의 출퇴근 기록을 조회하는 메소드

    Optional<Attendance> findByEmployeeId(Long employeeId);

    void delete(Attendance attendance);
}
