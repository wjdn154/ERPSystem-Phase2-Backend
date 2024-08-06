package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountSubjectStandardFinancialStatementRepositoryImpl implements AccountSubjectStandardFinancialStatementRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}