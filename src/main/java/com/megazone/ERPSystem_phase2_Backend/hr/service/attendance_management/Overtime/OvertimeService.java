package com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Overtime;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Overtime;

import java.util.Optional;

public interface OvertimeService {
    Optional<Overtime> saveOvertime(Overtime overtime);
    Optional<Overtime> updateOvertime(Long id, Overtime overtime);
    Optional<Overtime> getOvertimeById(Long id);
    void deleteOvertime(Long id);
}
