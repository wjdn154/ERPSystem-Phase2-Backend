package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Role;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>, RoleRepositoryCustom {
}
