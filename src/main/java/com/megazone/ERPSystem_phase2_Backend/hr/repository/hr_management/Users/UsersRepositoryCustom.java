package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.UsersDTO;

import java.util.List;

public interface UsersRepositoryCustom {
    List<UsersDTO> findAllUsers();
}
