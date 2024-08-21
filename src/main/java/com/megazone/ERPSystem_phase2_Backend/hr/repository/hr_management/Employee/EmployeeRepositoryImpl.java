package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.EmployeeDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EmployeeDTO> findAllEmployee() {
        return List.of();
    }
}
