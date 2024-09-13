package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersPermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface UsersService {
    List<UsersShowDTO> findAllUsers();

    ResponseEntity<String> registerUser(AuthRequest authRequest) throws SQLException;

    UsersShowDTO findUserById(Long id);

    UsersShowDTO updateUser(Long id, UsersShowDTO usersDTO);

    void deleteUsers(Long usersId);

    //UsersResponseDTO assignRoleToUser(Long userId, Long roleId);

    UsersPermissionDTO assignPermissionToUser(Long userId, Long permissionId);

    //Role assignPermissionToRole(Long roleId, Long permissionId);
}
