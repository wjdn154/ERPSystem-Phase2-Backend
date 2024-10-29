package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.QAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.QSalary;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.QSalaryAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SalaryAllowanceRepositoryImpl implements SalaryAllowanceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SalaryLedgerAllowanceShowDTO> findSalaryAllowanceList(Long salaryId) {

        QSalaryAllowance salaryAllowance = QSalaryAllowance.salaryAllowance;
        QSalary salary = QSalary.salary;
        QAllowance allowance = QAllowance.allowance;

        return jpaQueryFactory.select(
                Projections.constructor(SalaryLedgerAllowanceShowDTO.class,
                        salaryAllowance.id,
                        allowance.taxType,
                        allowance.name,
                        salaryAllowance.amount
                ))
                .from(salaryAllowance)
                .join(salary).on(salary.id.eq(salaryAllowance.salary.id))
                .join(salaryAllowance).on(allowance.id.eq(salaryAllowance.allowance.id))
                .where(salaryAllowance.salary.id.eq(salaryId))
                .fetch();
    }
}
