package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import lombok.Setter;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.SQLException;
import java.util.Map;

@Setter
@Component
//@RequestScope  // 각 요청마다 별도 스코프에서 동작하도록 설정
public class SchemaBasedTenantIdentifierResolver implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer {

    // 기본 테넌트 식별자를 "unknown"으로 설정하되, 요청에 따라 변경될 수 있음.
    private String currentTenant = "unknown";

    // 외부에서 테넌트를 설정할 수 있도록 setter 메서드 제공.
    public void setCurrentTenant(String tenantId) {
        this.currentTenant = tenantId;
        System.out.println("setCurrentTenant 호출됨: " + tenantId);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        System.out.println("resolveCurrentTenantIdentifier 호출됨: currentTenant = " + currentTenant);
        return currentTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}