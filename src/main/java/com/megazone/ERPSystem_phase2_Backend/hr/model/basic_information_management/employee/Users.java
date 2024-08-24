package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // 역할 ( System Administrator : 시스템 관리자, HR Staff : 인사 관리자, Finance Staff : 재무 담당자, Sales Staff : 생산 담당자
                        // Logistics Staff : 물류 담당자, General User : 일반  사용자( 본인의 정보 조회 및 수정, 휴가 신청, 성과 조회 등의 기능을 수행
                        //다른 부서나 모듈의 데이터에는 접근 권한이 제한됨.)
                        // 승인권자 : Approver => 휴가 신청, 인사 발령, 구매 요청 등의 승인 작업을 수행. 특정 모듈에 대한 승인 권한을 가짐.

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable = true)  // 사원이랑 1대1 참조, 무조건 사원이 아닐 수도 있음
    private Employee employee;

    @Column(nullable = false)
    private String userName;  // 사용자 이름

    @Column(nullable = false)
    private String password; // 비밀번호
}
