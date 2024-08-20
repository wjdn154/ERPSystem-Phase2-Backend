package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.EmployeeDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<EmployeeDTO> findAllEmployee();
}
