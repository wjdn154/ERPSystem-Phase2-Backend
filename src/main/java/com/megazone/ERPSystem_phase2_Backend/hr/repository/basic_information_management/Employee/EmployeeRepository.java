package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
    //Optional<Employee> findByFirstNameANDLastName(String firstname, String lastname);
    Optional<Employee> findById(Long id);

    //void deleteByEvaluatorId(Long );
    //findAllEmployees
}
