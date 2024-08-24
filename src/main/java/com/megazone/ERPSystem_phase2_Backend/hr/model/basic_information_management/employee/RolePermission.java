package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity(name="users_rolepermission")
@Table(name="users_rolepermission")
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // 역할 참조

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission; // 권한 참조
}
