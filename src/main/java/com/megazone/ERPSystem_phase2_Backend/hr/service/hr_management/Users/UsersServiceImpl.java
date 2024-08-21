package com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.RoleDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.UsersDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService{
    //private final UsersRepository usersRepository;
//
    //public UsersDTO getUsersDTO(Long id){
    //    Users users = usersRepository.findById(id)
    //            .orElseThrow(()-> new EntityNotFoundException("Users not found"));
//
    //    UsersDTO dto = new UsersDTO();
    //    dto.setId(users.getId());
    //    dto.setRoleType(users.getRole().getRoleType());
    //    dto.setEmployee(users.getEmployee());
    //    dto.setUserName(users.getUserName());
    //    dto.setPassword(users.getPassword());
//
    //    return dto;
    //}
//
    //@Override
    //public List<UsersDTO> findAllUsers() {
    //    return usersRepository.findAll().stream()
    //            .map(users -> new UsersDTO(
    //                    users.getId(),
    //                    users.getRole().getRoleType(),
    //                    users.getEmployee(),
    //                    users.getUserName(),
    //                    users.getPassword()
    //            ))
    //            .toList();
    //}
    private final UsersRepository usersRepository;

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
    public Users saveUsers(Users users) {
        return null;
    }

    @Override
    public Users updateUsers(Users users) {
        return null;
    }

    @Override
    public void deleteUsers(Long usersId) {

    }
}
