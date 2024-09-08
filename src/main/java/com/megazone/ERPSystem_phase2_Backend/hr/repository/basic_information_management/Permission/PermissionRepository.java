package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Permission;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long>, PermissionRepositoryCustom {
    //Optional<Permission> findById(Long id);

}
