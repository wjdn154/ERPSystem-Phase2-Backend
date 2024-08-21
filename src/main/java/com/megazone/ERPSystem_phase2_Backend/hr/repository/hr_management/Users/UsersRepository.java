package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Role;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long>, UsersRepositoryCustom {
    Optional<Users> findById(Long id);
    //Optional<Users> findByRole(Role role);

}
