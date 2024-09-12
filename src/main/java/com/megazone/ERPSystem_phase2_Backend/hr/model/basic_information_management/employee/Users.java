package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ERP 시스템 사용자의 역할 및 권한 정보 저장

@Data
@Entity(name="users")
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="permission_id",nullable=false)
    private Permission permission;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable = true)  // 사원이랑 1대1 참조, 무조건 사원이 아닐 수도 있음
    private Employee employee;

    @Column(nullable = false)
    private String userNickname;  // 사용자 이름

    @Column(nullable = false, name = "user_name", unique = true)
    private String userName;

    @Column(nullable = false)
    private String password; // 비밀번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 정보
}
