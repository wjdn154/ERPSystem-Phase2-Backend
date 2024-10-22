package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
    Department findByDepartmentName(String fromDepartmentName);

    Optional<Department> findByDepartmentCode(String code);
}
