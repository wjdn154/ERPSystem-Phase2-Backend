package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRelationship;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountSubjectRelationshipRepositoryImpl implements AccountSubjectRelationshipRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}