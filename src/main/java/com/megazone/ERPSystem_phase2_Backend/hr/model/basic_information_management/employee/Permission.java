package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.PermissionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// 권한 엔티티

@Data
@Entity(name="users_permission")
@Table(name="users_permission")
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolePermission> rolePermission; // 역할과의 관계

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionName permissionName; // 권한 이름
}
