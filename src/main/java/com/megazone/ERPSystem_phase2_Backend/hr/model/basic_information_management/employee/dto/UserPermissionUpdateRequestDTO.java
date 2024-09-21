package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import lombok.Data;

@Data
public class UserPermissionUpdateRequestDTO {
    private String username;
    private PermissionDTO permissionDTO;
}
