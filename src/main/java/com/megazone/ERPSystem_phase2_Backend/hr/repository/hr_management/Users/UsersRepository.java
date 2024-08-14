package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long>, UsersRepositoryCustom {
}
