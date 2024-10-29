package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long>, UsersRepositoryCustom {
    //Optional<Users> findById(Long id);
    // 사용자 ID로 사용자 검색
    Optional<Users> findByUserName(String userName);

    @Query ("select u from users u where u.employee.id = ?1")
    Optional<Users> findByEmployeeId(Long employeeId);

    //Optional<Users> findByRole(Role role);

}
