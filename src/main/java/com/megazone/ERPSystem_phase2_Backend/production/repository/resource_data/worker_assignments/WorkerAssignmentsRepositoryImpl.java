package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkerAssignmentsRepositoryImpl implements WorkerAssignmentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

}
