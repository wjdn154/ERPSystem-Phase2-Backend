package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersPermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;

import java.util.List;

public interface UsersService {
    List<UsersShowDTO> findAllUsers();

    UsersShowDTO createUser(UsersShowDTO usersDTO);

    UsersShowDTO findUserById(Long id);

    UsersShowDTO updateUser(Long id, UsersShowDTO usersDTO);

    void deleteUsers(Long usersId);

    UsersResponseDTO assignRoleToUser(Long userId, Long roleId);

    UsersPermissionDTO assignPermissionToUser(Long userId, Long permissionId);

    //Role assignPermissionToRole(Long roleId, Long permissionId);
}
