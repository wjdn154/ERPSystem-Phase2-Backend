package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Role;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long>, RoleRepositoryCustom {
}
