package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// 권한 엔티티

@Data
@Entity(name="users_permission")
@Table(name="users_permission")
@NoArgsConstructor
@AllArgsConstructor
public class Permission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일반 권한
    private Boolean readPrivileges = false;   // 데이터 또는 리소스를 읽을 수 있는 권한
    private Boolean writePrivileges = false;  // 데이터 또는 리소스를 생성하거나 수정할 수 있는 권한
    private Boolean deletePrivileges = false; // 데이터 또는 리소스를 삭제할 수 있는 권한
    private Boolean updatePrivileges = false; // 데이터 또는 리소스를 업데이트할 수 있는 권한

    // 인사(Human Resources) 관련 권한
    private Boolean hrView = false;          // 인사 데이터 조회 권한
    private Boolean hrEdit = false;          // 인사 데이터 수정 권한
    private Boolean hrDelete = false;        // 인사 데이터 삭제 권한

    // 재무(Finance) 관련 권한
    private Boolean financeView = false;     // 재무 데이터 조회 권한
    private Boolean financeEdit = false;     // 재무 데이터 수정 권한
    private Boolean financeDelete = false;   // 재무 데이터 삭제 권한

    // 생산(Production) 관련 권한
    private Boolean productionView = false;  // 생산 데이터 조회 권한
    private Boolean productionEdit = false;  // 생산 데이터 수정 권한
    private Boolean productionDelete = false; // 생산 데이터 삭제 권한

    // 물류(Logistics) 관련 권한
    private Boolean logisticsView = false;   // 물류 데이터 조회 권한
    private Boolean logisticsEdit = false;   // 물류 데이터 수정 권한
    private Boolean logisticsDelete = false; // 물류 데이터 삭제 권한

    // 기타 권한
    private Boolean adminPrivileges = false;  // 시스템 관리자 권한
    private Boolean configurePrivileges = false; // 시스템 설정 권한
    private Boolean manageUsers = false;       // 사용자 관리 권한
    //private Boolean manageRoles = false;       // 역할 관리 권한
    //private Boolean accessAdminPanel = false;  // 관리자 패널 접근 권한
    //private Boolean viewAuditLogs = false;     // 감사 로그 보기 권한
    //private Boolean manageSystemSettings = false; // 시스템 설정 관리 권한
    //private Boolean deployApplication = false; // 애플리케이션 배포 권한
    //private Boolean monitorSystemHealth = false; // 시스템 상태 모니터링 권한
    //private Boolean manageSecurityPolicies = false; // 보안 정책 관리 권한
    //private Boolean backupData = false;        // 데이터 백업 권한
    //private Boolean restoreData = false;       // 데이터 복원 권한
    //private Boolean manageNetworkConfiguration = false; // 네트워크 설정 관리 권한
    //private Boolean restartServices = false;   // 서비스 재시작 권한
    //private Boolean manageDatabase = false;    // 데이터베이스 관리 권한
    //private Boolean manageApiKeys = false;     // API 키 관리 권한
    //private Boolean viewSystemLogs = false;    // 시스템 로그 보기 권한
    //private Boolean shutdownSystem = false;    // 시스템 종료 권한
    //private Boolean updateSoftware = false;    // 소프트웨어 업데이트 권한
    //private Boolean manageDataRetention = false; // 데이터 보존 정책 관리 권한
}
