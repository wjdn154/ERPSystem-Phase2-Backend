package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Employee;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl {
    private final JPAQueryFactory queryFactory;
}
