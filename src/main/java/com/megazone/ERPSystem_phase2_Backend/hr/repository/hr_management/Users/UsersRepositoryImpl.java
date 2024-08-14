package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl {
    private final JPAQueryFactory queryFactory;
}
