package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 시스템 사용자의 역할 및 권한 정보 저장
// 역할 ( 시스템 관리자, 인사 담당자, 물류 담당자, 재무/회계 담당자, 생산 담당자 )
@Data
@Entity(name="users_role")
@Table(name="users_role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)// 사용자 참조
    private List<Users> users;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolePermission> rolePermission;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    @Column(nullable = false, unique = true)
    private RoleType roleType;

    @Column(nullable = false)
    private String roleName; // 역할 이름 (선택적, enum 대신 사용하거나 추가 정보로 남길 수 있음)
}