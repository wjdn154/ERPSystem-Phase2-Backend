package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 권한 엔티티

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolePermission> rolePermission; // 역할과의 관계

    @Column(nullable = false)
    private String permissionName; // 권한 이름
}
