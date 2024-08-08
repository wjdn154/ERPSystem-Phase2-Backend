package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ERP 시스템 사용자의 역할 및 권한 정보 저장

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role; // 역할 ( System Administrator : 시스템 관리자 , HR Staff : 인사 관리자 ,

}
