package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantMigrationService {

    private final JdbcTemplate jdbcTemplate;

    public TenantMigrationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void migrateTenant(String tenantSchema) {
        // Flyway 마이그레이션을 위한 동적 스키마 설정
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcTemplate.getDataSource())  // 데이터베이스 연결 정보
                .schemas(tenantSchema)  // 동적으로 받은 스키마 설정
                .locations("classpath:db/migration")  // 마이그레이션 파일 경로
                .baselineOnMigrate(true)  // 기존 스키마에 마이그레이션 시작
                .load();

        flyway.migrate();  // 마이그레이션 실행
    }
}