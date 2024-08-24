package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeShowDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
//    Optional<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO);
//
//
//    Optional<EmployeeDTO> getEmployeeById(Long id);

    List<EmployeeShowDTO> findAllEmployees();


    void deleteEmployee(Long employeeid);

    Optional<EmployeeDTO> findEmployeeById(Long id);

    Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO);

    Optional<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO);

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
