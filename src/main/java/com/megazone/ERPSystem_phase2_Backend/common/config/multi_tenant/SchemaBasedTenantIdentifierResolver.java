package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import lombok.Setter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.SQLException;
import java.util.Map;

/**
 * 각 요청에 따라 현재 테넌트를 식별하고, 없을 경우 기본 스키마를 사용함.
 */
@Setter
@Component
public class SchemaBasedTenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    /**
     * 현재 테넌트 식별자를 반환함.
     * @return String 현재 테넌트 ID 또는 기본 스키마 "PUBLIC"
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        // TenantContext에 테넌트가 설정되어 있으면 해당 테넌트 반환
        if (TenantContext.getCurrentTenant() != null) {
            return TenantContext.getCurrentTenant();
        }
        // 기본 스키마 반환
        return "PUBLIC";
    }

    /**
     * 기존 세션을 유지할지 여부를 결정함.
     * @return boolean 항상 false 반환
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    /**
     * Hibernate 설정에 다중 테넌트 식별자 해결자를 등록함.
     * @param hibernateProperties Hibernate 설정 맵
     */
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        // Hibernate 설정에 테넌트 식별자 해결자 등록
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}