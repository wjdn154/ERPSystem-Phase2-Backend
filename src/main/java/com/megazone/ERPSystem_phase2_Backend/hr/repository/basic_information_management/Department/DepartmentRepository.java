package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
    Department findByDepartmentName(String fromDepartmentName);

    Optional<Department> findByDepartmentCode(String code);

    Department findDepartmentById(Long id);

    @Query(value = "SELECT * FROM employee_department as s WHERE s.id = :id", nativeQuery = true)
    Department findTest(@Param("id") Long id);
}
