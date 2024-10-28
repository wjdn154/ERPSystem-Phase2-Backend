package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.TransferType;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransferTypeRepositoryImpl implements  TransferTypeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
