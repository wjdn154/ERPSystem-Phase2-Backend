package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectTransferMemo;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountSubjectTransferMemoRepositoryImpl implements AccountSubjectTransferMemoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}