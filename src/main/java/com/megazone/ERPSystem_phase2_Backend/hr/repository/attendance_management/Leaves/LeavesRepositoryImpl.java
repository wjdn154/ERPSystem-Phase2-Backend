package com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Leaves;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeavesRepositoryImpl implements LeavesRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
