package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.UsersDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UsersDTO> findAllUsers() {
        return List.of();
    }
}
