package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Role;
import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums.RoleType;
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
