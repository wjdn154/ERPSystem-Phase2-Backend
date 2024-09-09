package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    // 사원 리스트 조회
    List<EmployeeShowDTO> findAllEmployees();

    // 사원 상세 조회
    Optional<EmployeeOneDTO> findEmployeeById(Long id);

    // 사원 수정
    Optional<EmployeeDTO> updateEmployee(Long id, EmployeeDataDTO dto);

    // 사원 등록. 저장
    Optional<EmployeeDTO> saveEmployee(EmployeeCreateDTO dto);


    // 사원 삭제
    void deleteEmployee(Long id);

    //void deleteEmployeeById(Long id);
}
