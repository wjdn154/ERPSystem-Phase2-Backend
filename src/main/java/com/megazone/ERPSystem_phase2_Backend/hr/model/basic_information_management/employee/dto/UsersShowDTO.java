package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersShowDTO {
    private Long id;
    private String usersId;
    private String userName;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String password;
    private String permissionId;
}
