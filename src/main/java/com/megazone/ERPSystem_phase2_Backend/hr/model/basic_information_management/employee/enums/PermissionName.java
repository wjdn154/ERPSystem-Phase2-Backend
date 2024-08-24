package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums;


public enum PermissionName {
    READ_PRIVILEGES, //데이터 또는 리소스를 읽을 수 있는 권한.
    WRITE_PRIVILEGES, // 데이터 또는 리소스를 생성하거나 수정할 수 있는 권한.
    DELETE_PRIVILEGES, // 데이터 또는 리소스를 삭제할 수 있는 권한.
    UPDATE_PRIVILEGES, // 데이터 또는 리소스를 업데이트할 수 있는 권한.
    ADMIN_PRIVILEGES, // 시스템 관리자 수준의 권한으로, 대부분의 작업을 수행할 수 있는 권한.
    CONFIGURE_PRIVILEGES, // 시스템 설정을 구성할 수 있는 권한.
    MANAGE_USERS,
    MANAGE_ROLES,
    VIEW_REPORTS,
    GENERATE_REPORTS,
    DOWNLOAD_REPORTS,
    VIEW_PROJECTS,
    MANAGE_PROJECTS,
    ASSIGN_TASKS,
    VIEW_TASKS,
    VIEW_FINANCIALS,
    MANAGE_BUDGETS,
    PROCESS_PAYMENTS,
    EXECUTE_ACTIONS,
    ACCESS_ADMIN_PANEL,
    VIEW_AUDIT_LOGS
}
