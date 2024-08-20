package com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums.EmploymentStatus;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
//    Optional<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO);
//
//    Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO);
//
//    Optional<EmployeeDTO> getEmployeeById(Long id);

    List<EmployeeDTO> findAllEmployees();

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeid);

//    List<EmployeeDTO> searchEmployeesByLastName(String lastName);
//
//    List<EmployeeDTO> searchEmployeesByDepartment(String departmentName);
//
//    Optional<EmployeeDTO> updateEmploymentStatus(Long id, EmploymentStatus newStatus);
//
//    EmployeeDTO convertToDTO(Employee employee);
//
//    Employee convertToEntity(EmployeeDTO employeeDTO);
//
//    List<EmployeeDTO> findAllEmployees();
//
//    Optional<EmployeeDTO> findEmployeeById(Long id);
}
