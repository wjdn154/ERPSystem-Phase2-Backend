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

    private final JdbcTemplate jdbcTemplate;  // JDBC 연동을 위한 JdbcTemplate
    private final EntityManagerFactory entityManagerFactory;  // 엔티티 관리를 위한 EntityManagerFactory
    private final SqlInitProperties sqlInitProperties;  // SQL 초기화 프로퍼티

    /**
     * 애플리케이션 시작 시 스키마 및 테넌트 초기화 진행
     *
     * @throws Exception 스키마 처리나 테넌트 등록 시 발생하는 예외 처리
     */
    @PostConstruct
    public void init() throws Exception {
        // PUBLIC 스키마 및 테넌트 스키마 처리
        handlePublicSchema();
        registerTenant("tenant_1");
        registerTenant("tenant_2");
    }

    /**
     * PUBLIC 스키마 초기화 및 테이블 생성, 초기값 삽입
     *
     * @throws Exception 스키마 생성 및 데이터 삽입 시 발생하는 예외 처리
     */
    private void handlePublicSchema() throws Exception {
        dropAndCreatePublicSchema();
        generateTablesForSchema("PUBLIC");
        insertInitialDataForSchema("PUBLIC");
    }

    /**
     * 테넌트 스키마 생성 및 테이블 생성, 초기값 삽입
     *
     * @param tenantId 테넌트의 스키마 이름
     * @throws Exception 테넌트 스키마 처리 시 발생하는 예외 처리
     */
    public void registerTenant(String tenantId) throws Exception {
        dropAndCreateSchema(tenantId);
        generateTablesForSchema(tenantId);
        insertInitialDataForSchema(tenantId);
    }

    /**
     * PUBLIC 스키마 삭제 및 생성
     *
     * @throws Exception 스키마 삭제 및 생성 중 발생하는 예외 처리
     */
    private void dropAndCreatePublicSchema() throws Exception {
        dropSchema("PUBLIC");
        createSchema("PUBLIC");
    }

    /**
     * 테넌트 스키마 삭제 및 생성
     *
     * @param tenantId 테넌트의 스키마 이름
     * @throws Exception 스키마 삭제 및 생성 중 발생하는 예외 처리
     */
    private void dropAndCreateSchema(String tenantId) throws Exception {
        dropSchema(tenantId);
        createSchema(tenantId);
    }

    /**
     * 스키마 삭제
     *
     * @param schemaName 삭제할 스키마 이름
     * @throws Exception 스키마 삭제 중 발생하는 예외 처리
     */
    private void dropSchema(String schemaName) throws Exception {
        String dropSchemaSQL = "DROP SCHEMA IF EXISTS " + schemaName;
        jdbcTemplate.execute(dropSchemaSQL);
    }

    /**
     * 스키마 생성
     *
     * @param schemaName 생성할 스키마 이름
     * @throws Exception 스키마 생성 중 발생하는 예외 처리
     */
    private void createSchema(String schemaName) throws Exception {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
        jdbcTemplate.execute(createSchemaSQL);
    }

    /**
     * Hibernate 엔티티 기반으로 특정 스키마에 테이블 생성
     *
     * @param schemaName 테이블을 생성할 스키마 이름
     * @throws Exception 테이블 생성 중 발생하는 예외 처리
     */
    private void generateTablesForSchema(String schemaName) throws Exception {
        // 1. 테넌트 스키마로 전환
        // 스키마 전환을 위해 현재 스키마를 설정함. 이를 통해 해당 스키마에 테이블을 생성함.
        switchToTenantSchema(schemaName);

        // 2. Hibernate 설정을 사용하여 StandardServiceRegistryBuilder 생성
        // Hibernate의 엔티티 매핑과 관련된 설정을 적용하여 StandardServiceRegistryBuilder를 생성함.
        // 이를 통해 Hibernate의 설정을 기반으로 테이블을 생성할 준비를 함.
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(entityManagerFactory.unwrap(SessionFactoryImplementor.class)
                        .getProperties());

        // 3. 엔티티 클래스 수집
        // JPA 메타모델을 사용하여 현재 EntityManagerFactory에 등록된 모든 엔티티 클래스를 수집함.
        // 이를 통해 해당 엔티티에 기반한 테이블을 생성할 수 있도록 함.
        Set<Class<?>> entityClasses = entityManagerFactory.getMetamodel().getEntities()
                .stream()
                .map(e -> e.getJavaType())  // 각 엔티티의 Java 타입을 추출함.
                .collect(java.util.stream.Collectors.toSet());  // 엔티티 클래스를 Set으로 수집함.

        // 4. MetadataSources 생성 및 엔티티 클래스 추가
        // 수집한 엔티티 클래스를 Hibernate의 MetadataSources에 추가함.
        // 이를 통해 Hibernate가 엔티티 클래스를 기반으로 테이블을 생성할 수 있게 함.
        MetadataSources metadataSources = new MetadataSources(registryBuilder.build());
        for (Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass);  // 각 엔티티 클래스를 메타데이터 소스에 추가함.
        }

        // 5. SchemaExport 설정
        // SchemaExport 객체를 생성하여 테이블 생성 전략을 설정함.
        // - 구분자(;)를 사용하여 SQL 명령어를 구분함.
        // - SQL 출력을 포맷팅함.
        // - 에러 발생 시 처리 중단하도록 설정함.
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setDelimiter(";");  // SQL 명령어 구분자로 세미콜론(;)을 사용함.
        schemaExport.setFormat(true);  // SQL 명령어를 보기 좋게 포맷팅함.
        schemaExport.setHaltOnError(true);  // 에러가 발생하면 처리를 중단함.

        // 6. 스키마에 테이블 생성 실행
        // 수집한 엔티티 클래스에 기반하여 실제 테이블을 생성하는 작업을 실행함.
        // - TargetType.DATABASE: 실제 데이터베이스에 테이블을 생성함.
        // - Action.CREATE: 엔티티 기반으로 테이블을 새로 생성함.
        schemaExport.execute(EnumSet.of(org.hibernate.tool.schema.TargetType.DATABASE),
                SchemaExport.Action.CREATE, metadataSources.buildMetadata());  // 메타데이터 기반으로 테이블 생성
    }

    /**
     * 특정 스키마에 초기값 삽입
     *
     * @param schemaName 초기값을 삽입할 스키마 이름
     * @throws Exception 초기값 삽입 중 발생하는 예외 처리
     */
    private void insertInitialDataForSchema(String schemaName) throws Exception {
        Path migrationDir = createTemporaryMigrationDir(schemaName);
        mergeAndExecuteSqlScriptsForTenant(migrationDir);
        applyFlywayMigrations(schemaName, migrationDir);
    }

    /**
     * 테넌트 스키마로 전환
     *
     * @param schemaName 전환할 스키마 이름
     * @throws Exception 스키마 전환 중 발생하는 예외 처리
     */
    private void switchToTenantSchema(String schemaName) throws Exception {
        TenantContext.setCurrentTenant(schemaName);
        jdbcTemplate.execute("USE " + schemaName);
    }

    /**
     * 마이그레이션을 위한 임시 디렉토리 생성
     *
     * @param schemaName 생성할 스키마 이름
     * @return 생성된 임시 디렉토리 경로
     * @throws Exception 디렉토리 생성 중 발생하는 예외 처리
     */
    private Path createTemporaryMigrationDir(String schemaName) throws Exception {
        return Files.createTempDirectory("flyway_migrations_" + schemaName);
    }

    /**
     * SQL 파일 병합 및 실행
     *
     * @param migrationDir 마이그레이션 디렉토리 경로
     * @throws Exception SQL 파일 병합 및 실행 중 발생하는 예외 처리
     */
    private void mergeAndExecuteSqlScriptsForTenant(Path migrationDir) throws Exception {
        String outputFilePath = migrationDir.resolve("V2__initial_database.sql").toString();
        mergeSqlFiles(sqlInitProperties.getDataLocations(), outputFilePath);
    }

    /**
     * SQL 파일 병합
     *
     * @param sqlFilePaths SQL 파일 경로 목록
     * @param outputFilePath 출력 파일 경로
     * @throws IOException 파일 병합 중 발생하는 예외 처리
     */
    private void mergeSqlFiles(List<String> sqlFilePaths, String outputFilePath) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String sqlFilePath : sqlFilePaths) {
                Resource[] resources = resolver.getResources(sqlFilePath);
                for (Resource resource : resources) {
                    appendFileContent(resource, writer);
                }
            }
        }
    }

    /**
     * 파일 내용 추가
     *
     * @param resource 추가할 리소스
     * @param writer 파일 작성기
     * @throws IOException 파일 추가 중 발생하는 예외 처리
     */
    private void appendFileContent(Resource resource, BufferedWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Flyway를 통한 DB 마이그레이션 실행
     *
     * @param tenantSchema 마이그레이션할 테넌트 스키마
     * @param migrationDir 마이그레이션 디렉토리 경로
     */
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
            flyway.repair();  // 오류 발생 시 Flyway 복구
        }
    }
}