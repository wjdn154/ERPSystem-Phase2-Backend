package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private RoleDTO role;
    private Long employeeId;
    private String userName;
    private String password;
}
