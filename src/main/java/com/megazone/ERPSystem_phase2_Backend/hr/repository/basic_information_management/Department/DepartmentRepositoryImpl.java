package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentShowDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentShowDTO> findAllDepartments() {
        return List.of();
    }
}
