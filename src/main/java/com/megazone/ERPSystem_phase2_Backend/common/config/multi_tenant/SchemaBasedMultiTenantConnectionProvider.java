package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider, HibernatePropertiesCustomizer {

    // DataSource를 통해 DB 연결을 관리하기 위해 주입됨
    @Autowired DataSource dataSource;

    // PUBLIC 스키마로부터 연결을 얻음. PUBLIC은 기본 스키마로 사용됨.
    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection("erp");
    }

    // 주어진 연결을 닫음
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    // 특정 테넌트 스키마에 맞는 연결을 반환함.
    // tenantIdentifier는 각 테넌트를 식별하는 ID임.
    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
//        if(TenantContext.getCurrentTenant() != null) {
//            tenantIdentifier = TenantContext.getCurrentTenant();
//        }

        // 로그로 테넌트 식별자를 확인
        System.out.println("연결용 테넌트 식별자: " + tenantIdentifier);

        final Connection connection = dataSource.getConnection(); // 기본 DataSource로부터 연결을 가져옴

        // MySQL에서 스키마 전환 (setSchema 대신 USE <schema_name> 사용)
        connection.createStatement().execute("USE " + tenantIdentifier);  // 스키마 전환 명령어 실행
        System.out.println("연결된 테넌트 스키마: " + tenantIdentifier);

        return connection;
    }

    /*
     * (non-Javadoc)
     * 테넌트 연결을 해제할 때 PUBLIC 스키마로 변경 후 연결을 닫음.
     */
    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema("erp"); // 연결을 닫기 전에 스키마를 erp로 변경함
        connection.close();
    }

    // 연결을 공격적으로 해제하는 방식은 지원하지 않음
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    // 이 클래스가 주어진 유형으로 래핑될 수 있는지 여부를 반환
    @Override
    public boolean isUnwrappableAs(Class<?> aClass) {
        return false;
    }

    // 클래스가 특정 유형으로 언래핑되는 것을 지원하지 않음
    @Override
    public <T> T unwrap(Class<T> aClass) {
        throw new UnsupportedOperationException("Can't unwrap this."); // 지원되지 않는 작업임
    }

    // Hibernate의 설정에 이 다중 테넌트 연결 공급자를 등록함
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}