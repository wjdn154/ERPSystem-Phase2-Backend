package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Department;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    // 부서 리스트 조회
    List<DepartmentShowDTO> findAllDepartments(Long company_id);

    // 부서 id 로 조회
    Optional<DepartmentShowDTO> findDepartmentById(Long id);

    // 부서 등록
    DepartmentCreateDTO saveDepartment(DepartmentDTO dto);

    boolean hasEmployees(Long id);

    void deleteDepartment(Long id);
}
