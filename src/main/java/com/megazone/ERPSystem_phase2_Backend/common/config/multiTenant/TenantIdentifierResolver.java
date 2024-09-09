package com.megazone.ERPSystem_phase2_Backend.common.config.multiTenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        // 여기서 현재 테넌트 식별자를 반환해야 함 (예: 세션이나 요청의 헤더에서 가져옴)
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}