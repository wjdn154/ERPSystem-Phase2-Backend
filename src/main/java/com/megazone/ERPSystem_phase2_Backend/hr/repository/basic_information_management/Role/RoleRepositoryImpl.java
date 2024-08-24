package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Role;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl {
    private final JPAQueryFactory queryFactory;
}
