package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QUsers;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Employee> findAllByUser() {
        QEmployee employee = QEmployee.employee;
        QUsers users = QUsers.users;

        return queryFactory
                .select(employee)
                .from(employee)
                .join(users).on(employee.email.eq(users.employee.email))
                .fetch();
    }
}
