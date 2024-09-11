package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.SchemaManagementTool;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * 각 테넌트의 스키마 생성, 테이블 생성, SQL 스크립트 실행 및 Flyway 마이그레이션을 관리함.
 * 애플리케이션 실행 시 초기 테넌트들을 자동으로 등록하며,
 * 각 테넌트별 스키마와 데이터를 개별적으로 관리할 수 있도록 지원함.
 */
@Service
@RequiredArgsConstructor
public class TenantService {

    private final JdbcTemplate jdbcTemplate; // JDBC 연동을 위한 JdbcTemplate 주입
    private final EntityManagerFactory entityManagerFactory; // 엔티티 관리를 위한 EntityManagerFactory 주입
    private final SqlInitProperties sqlInitProperties; // SQL 초기화 프로퍼티를 위한 SqlInitProperties 주입

    /**
     * 애플리케이션 시작 시 테넌트 등록
     */
    @PostConstruct
    public void init() {
        registerTenant("tenant_1");
        registerTenant("tenant_2");
    }

    /**
     * 주어진 테넌트 ID로 테넌트 등록
     *
     * @param tenantId 테넌트 ID
     */
    public void registerTenant(String tenantId) {
        createSchema(tenantId); // 스키마 생성
        switchToTenantSchema(tenantId); // 스키마 전환
        generateTenantTables(); // 테이블 생성
        mergeAndExecuteSqlScriptsForTenant(); // SQL 스크립트 병합 및 실행
        applyFlywayMigrations(tenantId); // Flyway 마이그레이션 적용
    }

    /**
     * 테넌트 스키마 생성
     *
     * @param tenantId 테넌트 ID
     */
    private void createSchema(String tenantId) {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + tenantId;
        jdbcTemplate.execute(createSchemaSQL); // 스키마 생성 SQL 실행
    }

    /**
     * 주어진 테넌트 스키마로 전환
     *
     * @param tenantId 테넌트 ID
     */
    private void switchToTenantSchema(String tenantId) {
        jdbcTemplate.execute("USE " + tenantId); // 스키마 전환 SQL 실행
    }

    /**
     * Hibernate 엔티티를 기반으로 테이블 생성
     */
    private void generateTenantTables() {
        // Hibernate 설정을 기반으로 테이블 생성
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.unwrap(SessionFactoryImplementor.class)
                        .getProperties());

        entityManagerFactory
                .unwrap(SessionFactoryImplementor.class)
                .getServiceRegistry()
                .getService(SchemaManagementTool.class);

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.getProperties())
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Set<Class<?>> entityClasses = entityManagerFactory.getMetamodel().getEntities()
                .stream().map(e -> e.getJavaType()).collect(java.util.stream.Collectors.toSet());

        for (Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass); // 모든 엔티티 추가
        }

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";"); // SQL 구문 끝에 세미콜론 추가
        schemaExport.setFormat(false); // SQL 포맷 비활성화
        schemaExport.setOutputFile("src/main/resources/db/migration/v1/V1__create_tables.sql"); // 테이블 생성 SQL 파일 지정
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadataSources.buildMetadata()); // 테이블 생성 실행

        StandardServiceRegistryBuilder.destroy(registryBuilder.build()); // 레지스트리 종료
    }

    /**
     * 테넌트용 SQL 스크립트를 병합하고 실행
     */
    private void mergeAndExecuteSqlScriptsForTenant() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            String outputFilePath = "src/main/resources/db/migration/v2/V2__initial_database.sql"; // 병합된 SQL 파일 경로
            mergeSqlFiles(sqlInitProperties.getDataLocations(), outputFilePath); // SQL 파일 병합
        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 처리
        }
    }

    /**
     * SQL 파일 병합
     *
     * @param sqlFilePaths 병합할 SQL 파일 경로 리스트
     * @param outputFilePath 병합 결과 파일 경로
     */
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
            e.printStackTrace(); // 파일 입출력 예외 처리
        }
    }

    /**
     * SQL 파일 내용을 병합 결과 파일에 추가
     *
     * @param resource 리소스 파일
     * @param writer 파일 작성자
     * @throws IOException 입출력 예외
     */
    private void appendFileContent(Resource resource, BufferedWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line); // 파일 내용 쓰기
                writer.newLine(); // 줄바꿈 추가
            }
        }
    }

    /**
     * Flyway 마이그레이션 실행
     *
     * @param tenantSchema 테넌트 스키마
     */
    public void applyFlywayMigrations(String tenantSchema) {
        Flyway flyway = Flyway.configure()
                .dataSource(jdbcTemplate.getDataSource()) // 데이터 소스 설정
                .schemas(tenantSchema) // 스키마 설정
                .locations("classpath:db/migration/v1") // V1 마이그레이션 파일 경로
                .baselineOnMigrate(true) // 기존 스키마 마이그레이션 설정
                .load();

        try {
            flyway.migrate(); // V1 마이그레이션 실행
        } catch (Exception e) {
            System.out.println("테이블 마이그레이션 실패: " + e.getMessage());
            flyway.repair(); // 오류 발생 시 복구
        }

        try {
            flyway = Flyway.configure()
                    .dataSource(jdbcTemplate.getDataSource()) // 데이터 소스 설정
                    .schemas(tenantSchema) // 스키마 설정
                    .locations("classpath:db/migration/v2") // V2 마이그레이션 파일 경로
                    .baselineOnMigrate(true) // 기존 스키마 마이그레이션 설정
                    .load();

            flyway.migrate(); // V2 마이그레이션 실행
        } catch (Exception e) {
            System.out.println("데이터 마이그레이션 실패: " + e.getMessage());
            flyway.repair(); // 오류 발생 시 복구
        }
    }
}