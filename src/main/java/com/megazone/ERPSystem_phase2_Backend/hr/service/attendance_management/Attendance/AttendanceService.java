package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Attendance;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Attendance;

import java.util.List;

public interface AttendanceService {

    //
    List<Attendance> getAttendanceByEmployeeId(Long employeeId);
}
