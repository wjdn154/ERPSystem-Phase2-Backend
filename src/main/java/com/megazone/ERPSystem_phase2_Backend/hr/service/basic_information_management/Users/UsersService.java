package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersDTO;

import java.util.List;

public interface UsersService {
    List<UsersDTO> findAllUsers();

    UsersDTO createUser(UsersDTO usersDTO);

    UsersDTO findUserById(Long id);

    UsersDTO updateUser(Long id, UsersDTO usersDTO);

    void deleteUsers(Long usersId);
}
