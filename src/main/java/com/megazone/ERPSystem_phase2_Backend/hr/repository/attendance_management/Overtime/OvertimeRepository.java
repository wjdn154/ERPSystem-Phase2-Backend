package com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Overtime;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OvertimeRepository extends JpaRepository<Overtime, Long>, OvertimeRepositoryCustom {
}
