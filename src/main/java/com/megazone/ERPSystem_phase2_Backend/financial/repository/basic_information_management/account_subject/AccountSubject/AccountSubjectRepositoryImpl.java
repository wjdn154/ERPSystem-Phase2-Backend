package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountSubjectRepositoryImpl implements AccountSubjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}