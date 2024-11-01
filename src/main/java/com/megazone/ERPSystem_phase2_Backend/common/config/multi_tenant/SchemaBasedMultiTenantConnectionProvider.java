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

/**
 * 테넌트별로 다른 스키마로의 연결을 관리
 */
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider, HibernatePropertiesCustomizer {

    // DB 연결 관리를 위한 DataSource 객체
    @Autowired
    private DataSource dataSource;

    /**
     * PUBLIC 스키마를 사용하는 기본 연결을 반환함.
     * @return Connection 기본 스키마로 연결된 Connection 객체
     * @throws SQLException SQL 오류 발생 시 예외 처리
     */
    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection("PUBLIC");
    }

    /**
     * 주어진 Connection을 해제함.
     * @param connection 해제할 Connection 객체
     * @throws SQLException SQL 오류 발생 시 예외 처리
     */
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * 주어진 테넌트 식별자에 해당하는 스키마로 전환 후 Connection 반환.
     * @param tenantIdentifier 테넌트를 식별하는 ID
     * @return Connection 테넌트 스키마로 연결된 Connection 객체
     * @throws SQLException SQL 오류 발생 시 예외 처리
     */
    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        // 현재 설정된 테넌트가 있으면 그 값을 사용함
        if (TenantContext.getCurrentTenant() != null) {
            tenantIdentifier = TenantContext.getCurrentTenant();
        }

        // 데이터베이스 연결 생성
        final Connection connection = dataSource.getConnection();

        // MySQL에서 스키마 전환 (setSchema 대신 USE 사용)
        connection.createStatement().execute("USE " + tenantIdentifier);

        return connection;
    }

    /**
     * 연결 해제 전 PUBLIC 스키마로 전환한 후 연결을 종료함.
     * @param tenantIdentifier 해제할 테넌트 식별자
     * @param connection 해제할 Connection 객체
     * @throws SQLException SQL 오류 발생 시 예외 처리
     */
    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema("PUBLIC"); // 해제 전에 PUBLIC 스키마로 전환
        connection.close();
    }

    /**
     * 연결을 공격적으로 해제하는 방식은 지원하지 않음.
     * @return boolean 항상 false 반환
     */
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    /**
     * 특정 유형으로 래핑이 가능한지 여부를 반환함.
     * @param aClass 검사할 클래스
     * @return boolean 래핑 가능 여부 반환 (항상 false)
     */
    @Override
    public boolean isUnwrappableAs(Class<?> aClass) {
        return false;
    }

    /**
     * 언래핑이 지원되지 않음을 명시적으로 선언함.
     * @param aClass 언래핑할 클래스
     * @param <T> 반환할 클래스 유형
     * @return T 언래핑되지 않음 (예외 발생)
     * @throws UnsupportedOperationException 지원되지 않는 작업
     */
    @Override
    public <T> T unwrap(Class<T> aClass) {
        throw new UnsupportedOperationException("이 작업은 지원되지 않습니다.");
    }

    /**
     * Hibernate 설정에 이 연결 제공자를 등록함.
     * @param hibernateProperties Hibernate 설정 맵
     */
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}