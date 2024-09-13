package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

/**
 * TenantContext 클래스는 스레드별로 테넌트 ID를 관리하기 위해 사용됨.
 * 각 스레드에서 별도의 테넌트 ID를 저장하고 조회할 수 있도록 ThreadLocal을 사용함.
 */
public class TenantContext {

    // 각 스레드마다 독립적인 테넌트 ID를 저장하기 위한 ThreadLocal 변수
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /**
     * 테넌트 ID를 설정함.
     * @param tenantId 설정할 테넌트 ID
     */
    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    /**
     * 현재 스레드의 테넌트 ID를 반환함.
     * @return 현재 설정된 테넌트 ID
     */
    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    /**
     * 현재 스레드의 테넌트 ID를 초기화함.
     */
    public static void clear() {
        currentTenant.remove();
    }
}