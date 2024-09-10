package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.SourceType;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.internal.ExceptionHandlerLoggedImpl;
import org.hibernate.tool.schema.spi.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantMigrationService tenantMigrationService;
    private final EntityManagerFactory entityManagerFactory;

    public void exportSchema(String tenantId) {
        // 1. 테넌트 스키마 생성
        createSchema(tenantId);

        // 2. Hibernate 엔티티를 기반으로 테이블 생성
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.unwrap(SessionFactoryImplementor.class)
                        .getProperties());

        Metadata metadata = new MetadataSources(registryBuilder.build()).buildMetadata();

        SchemaManagementTool schemaManagementTool = entityManagerFactory
                .unwrap(SessionFactoryImplementor.class)
                .getServiceRegistry()
                .getService(SchemaManagementTool.class);


        // Hibernate 설정을 로드하여 스키마 정보를 가져옴
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.getProperties())
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Set<Class<?>> entityClasses = entityManagerFactory.getMetamodel().getEntities()
                .stream().map(e -> e.getJavaType()).collect(java.util.stream.Collectors.toSet());

        // 모든 엔티티 추가
        for (Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass);
        }

//        Metadata metadatas = metadataSources.buildMetadata();
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        // Hibernate를 통해 엔티티 기반 DDL 생성
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.setOutputFile("src/main/resources/db/migration/V1__create_tables.sql");  // 테넌트별 마이그레이션 파일 생성
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadataSources.buildMetadata());

        StandardServiceRegistryBuilder.destroy(serviceRegistry);



        // Hibernate 설정을 담은 Map 객체 생성
        Map<String, Object> schemaOptions = entityManagerFactory.unwrap(SessionFactoryImplementor.class)
                .getProperties();

        SchemaCreator schemaCreator = schemaManagementTool.getSchemaCreator(schemaOptions);

        schemaCreator.doCreation(
                metadata,                    // Hibernate 메타데이터
                new CustomExecutionOptions(), // 실행 옵션
                contributed -> true,          // 모든 데이터베이스 객체 포함
                new CustomSourceDescriptor(), // 소스 디스크립터: null 대신 사용
                new CustomTargetDescriptor()  // 타겟 디스크립터
        );

        // 3. Flyway 마이그레이션 적용
        tenantMigrationService.migrateTenant(tenantId);
    }

    private void createSchema(String tenantId) {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + tenantId;
        jdbcTemplate.execute(createSchemaSQL);
    }

    // Custom ExecutionOptions
    private static class CustomExecutionOptions implements ExecutionOptions {
        @Override
        public Map<String, Object> getConfigurationValues() {
            return Collections.emptyMap();
        }

        @Override
        public boolean shouldManageNamespaces() {
            return true;
        }

        @Override
        public ExceptionHandler getExceptionHandler() {
            return ExceptionHandlerLoggedImpl.INSTANCE;
        }
    }

    // CustomTargetDescriptor 정의
    private static class CustomTargetDescriptor implements TargetDescriptor {
        @Override
        public EnumSet<TargetType> getTargetTypes() {
            return EnumSet.of(TargetType.DATABASE);
        }

        @Override
        public ScriptTargetOutput getScriptTargetOutput() {
            return null;
        }
    }

    private static class CustomSourceDescriptor implements SourceDescriptor {
        @Override
        public SourceType getSourceType() {
            return SourceType.METADATA;
        }

        @Override
        public ScriptSourceInput getScriptSourceInput() {
            return null;
        }
    }
}