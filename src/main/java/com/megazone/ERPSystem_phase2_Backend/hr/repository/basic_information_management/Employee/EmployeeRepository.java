package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Employee 엔티티와 ID 타입(Long)을 정의
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    Optional<Employee> findByEmployeeNumber(String employeeNumber);

}