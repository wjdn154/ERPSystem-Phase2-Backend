package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final JdbcTemplate jdbcTemplate; // JDBC 연동을 위한 JdbcTemplate 주입
    private final EntityManagerFactory entityManagerFactory; // 엔티티 관리를 위한 EntityManagerFactory 주입
    private final SqlInitProperties sqlInitProperties; // SQL 초기화 프로퍼티를 위한 SqlInitProperties 주입

    // 애플리케이션 시작 시 테넌트 등록
    @PostConstruct
    public void init() {
        registerTenant("tenant_1");
//        registerTenant("tenant_2");
//        registerTenant("tenant_3");
    }

    // 테넌트 초기 세팅
    public void registerTenant(String tenantId) {
        createSchema(tenantId); // 스키마 생성
        switchToTenantSchema(tenantId); // 스키마 전환
        Path migrationDir = createTemporaryMigrationDir(tenantId); // 마이그레이션 임시 디렉토리 생성
        generateTenantTables(migrationDir); // Hibernate 엔티티를 기반으로 테이블 SQL 파일 생성
        mergeAndExecuteSqlScriptsForTenant(migrationDir); // 초기값 SQL 파일 생성
        applyFlywayMigrations(tenantId, migrationDir); // DB 마이그레이션
    }

    // 테넌트 스키마 생성
    private void createSchema(String tenantId) {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + tenantId;
        jdbcTemplate.execute(createSchemaSQL);
    }

    // 스키마 전환
    private void switchToTenantSchema(String tenantId) {
        TenantContext.setCurrentTenant(tenantId);
        jdbcTemplate.execute("USE " + tenantId);
    }

    // 마이그레이션 임시 디렉토리 생성
    private Path createTemporaryMigrationDir(String tenantId) {
        try {
            return Files.createTempDirectory("flyway_migrations_" + tenantId);
        } catch (Exception e) {
            throw new RuntimeException("임시 디렉토리 생성 실패: " + e.getMessage(), e);
        }
    }

    // Hibernate 엔티티를 기반으로 테이블 SQL 파일 생성
    private void generateTenantTables(Path migrationDir) {
        // Hibernate 설정을 기반으로 테이블 생성
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.unwrap(SessionFactoryImplementor.class)
                        .getProperties());

        Set<Class<?>> entityClasses = entityManagerFactory.getMetamodel().getEntities()
                .stream()
                .map(e -> e.getJavaType())
//                .filter(entityClass -> !entityClass.getSimpleName().contains("company")
//                        && !entityClass.getSimpleName().contains("company_address")
//                        && !entityClass.getSimpleName().contains("company_admin")
//                        && !entityClass.getSimpleName().contains("company_corporate_kind")
//                        && !entityClass.getSimpleName().contains("company_corporate_type")
//                        && !entityClass.getSimpleName().contains("company_main_business")
//                        && !entityClass.getSimpleName().contains("company_representative")
//                        && !entityClass.getSimpleName().contains("company_tax_office"))
                .collect(java.util.stream.Collectors.toSet());

        MetadataSources metadataSources = new MetadataSources(registryBuilder.build());
        for (Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass);
        }

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.setHaltOnError(true);

        // 출력 파일 경로를 임시 디렉토리로 설정
        String outputFilePath = migrationDir.resolve("V1__create_tables.sql").toString();
        schemaExport.setOutputFile(outputFilePath);

        schemaExport.execute(EnumSet.of(org.hibernate.tool.schema.TargetType.SCRIPT),
                SchemaExport.Action.CREATE, metadataSources.buildMetadata());
    }

    // 초기값 SQL 파일 생성
    private void mergeAndExecuteSqlScriptsForTenant(Path migrationDir) {
        // 데이터 초기화 SQL 파일을 임시 디렉토리에 생성
        String outputFilePath = migrationDir.resolve("V2__initial_database.sql").toString();
        mergeSqlFiles(sqlInitProperties.getDataLocations(), outputFilePath); // SQL 파일 병합
    }

    // SQL 파일 병합
    private void mergeSqlFiles(List<String> sqlFilePaths, String outputFilePath) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String sqlFilePath : sqlFilePaths) {
                Resource[] resources = resolver.getResources(sqlFilePath);
                for (Resource resource : resources) {
                    appendFileContent(resource, writer); // 파일 내용 추가
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일 내용 추가
    private void appendFileContent(Resource resource, BufferedWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // DB 마이그레이션 실행
    private void applyFlywayMigrations(String tenantSchema, Path migrationDir) {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcTemplate.getDataSource())
                .schemas(tenantSchema)
                .locations("filesystem:" + migrationDir.toAbsolutePath().toString())
                .baselineOnMigrate(true)
                .load();

        try {
            flyway.migrate();
        } catch (Exception e) {
            System.out.println("마이그레이션 실패: " + e.getMessage());
            flyway.repair();
        }
    }

}