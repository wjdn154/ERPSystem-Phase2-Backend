package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersShowDTO {
    private Long id;
    private String userName;
    private String userNickname;
    private String employeeNumber;
    private String employeeName;
    private String password;
    private Long permissionId;

    public static UsersShowDTO toDTO(Users users) {
        return new UsersShowDTO(
                users.getId(),
                users.getUserName(),
                users.getUserNickname(),
                users.getEmployee().getEmployeeNumber(),
                users.getEmployee().getLastName()+users.getEmployee().getFirstName(),
                users.getPassword(),
                users.getPermission().getId()
        );
    }
}
