package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;

import java.util.List;

public interface UsersRepositoryCustom {
    List<UsersShowDTO> findAllUsers();
}
