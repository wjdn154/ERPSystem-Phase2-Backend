package com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.LeavesType;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeavesTypeRepositoryImpl implements LeavesTypeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
