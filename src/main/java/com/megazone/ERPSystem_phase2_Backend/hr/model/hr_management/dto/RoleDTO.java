package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto;


import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private RoleType roleType;
    private String roleName;
    // 필요한 경우, 사용자의 정보 포함
    // private List<UsersDTO> users;
}
