package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long>, UsersRepositoryCustom {
    //Optional<Users> findById(Long id);
    // 사용자 ID로 사용자 검색
    Users findByUsersId(String usersId);
    //Optional<Users> findByRole(Role role);

}
