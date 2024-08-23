package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QProcessDetails;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProcessDetailsRepositoryImpl implements ProcessDetailsRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QProcessDetails processDetails = QProcessDetails.processDetails;

    @Override
    public List<ProcessDetails> findByCodeOrNameContaining(String keyword) {

        return queryFactory.selectFrom(processDetails)
                .where(processDetails.code.containsIgnoreCase(keyword)
                        .or(processDetails.name.containsIgnoreCase(keyword)))
                .fetch();
    }
}
