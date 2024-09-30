package com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Leaves;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeavesRepository extends JpaRepository<Leaves, Long>, LeavesRepositoryCustom {
}
