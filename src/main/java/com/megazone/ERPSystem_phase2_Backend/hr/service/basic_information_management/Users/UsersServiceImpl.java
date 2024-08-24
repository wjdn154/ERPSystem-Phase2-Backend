package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Role;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.RoleDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Role.RoleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService{
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    public List<UsersDTO> findAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UsersDTO convertToDTO(Users users) {
        UsersDTO dto = new UsersDTO();
        dto.setId(users.getId());
        dto.setEmployeeId(users.getEmployee() != null ? users.getEmployee().getId() : null);
        dto.setUserName(users.getUserName());
        dto.setPassword(users.getPassword());

        // Convert Role to RoleDTO
        if (users.getRole() != null) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(users.getRole().getId());
            roleDTO.setRoleName(users.getRole().getRoleName());
            roleDTO.setRoleType(users.getRole().getRoleType());
            dto.setRole(roleDTO);
        }

        return dto;
    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        if (usersDTO.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        Users users = new Users();
        users.setUserName(usersDTO.getUserName());
        users.setPassword(usersDTO.getPassword());

        Role role = roleRepository.findById(usersDTO.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        users.setRole(role);

        Users savedUser = usersRepository.save(users);
        return convertToDTO(savedUser);
    }

    @Override
    public UsersDTO findUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public UsersDTO updateUser(Long id, UsersDTO usersDTO) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserName(usersDTO.getUserName());
        user.setPassword(usersDTO.getPassword());

        Role role = roleRepository.findById(usersDTO.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        Users updatedUser = usersRepository.save(user);
        return convertToDTO(updatedUser);

    }

    @Override
    public void deleteUsers(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        usersRepository.delete(user);
    }

//    private UsersDTO convertToDTO(Users user) {
//        RoleDTO roleDTO = new RoleDTO(
//                user.getRole().getId(),
//                user.getRole().getRoleName(),
//                user.getRole().getRoleType(),
//                user.getRole().getPermissions().stream()
//                        .map(permission -> new PermissionDTO(permission.getId(), permission.getPermissionName()))
//                        .collect(Collectors.toList())
//        );
//        return new UsersDTO(user.getId(), roleDTO, user.getUserName(), user.getPassword());
//    }
}
