package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private UsersRepository usersRepository;

    public Users saveUsers(Users users){
        return usersRepository.save(users);
    }

    @Override
    public List<UsersDTO> findAllUsers() {
        return List.of();
    }


}
