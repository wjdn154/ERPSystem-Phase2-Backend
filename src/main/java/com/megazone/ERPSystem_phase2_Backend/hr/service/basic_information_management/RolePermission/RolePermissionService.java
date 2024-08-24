package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.RolePermission;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.RoleDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.RolePermissionDTO;

public interface RolePermissionService {
    RoleDTO createRole(RoleDTO roleDTO);

    PermissionDTO createPermission(PermissionDTO permissionDTO);

    RolePermissionDTO assignPermissionToRole(RolePermissionDTO rolePermissionDTO);
}
