package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QProcessDetails;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProcessDetailsRepositoryImpl implements ProcessDetailsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProcessDetails> findByCode(String code) {
        QProcessDetails processDetails = QProcessDetails.processDetails;
        ProcessDetails result = queryFactory.selectFrom(processDetails)
                .where(processDetails.code.eq(code))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
