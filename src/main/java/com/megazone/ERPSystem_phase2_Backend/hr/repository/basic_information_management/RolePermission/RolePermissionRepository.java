package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.RolePermission;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
