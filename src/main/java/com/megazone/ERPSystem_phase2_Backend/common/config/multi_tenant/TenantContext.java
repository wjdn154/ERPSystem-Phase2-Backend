package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    // 테넌트 ID 설정
    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    // 현재 테넌트 ID를 반환
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    // 테넌트 ID 초기화
    public static void clear() {
        currentTenant.remove();
    }
}