//package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;
//
//import org.hibernate.tool.schema.TargetType;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.EnumSet;
//import java.util.List;
//
//@Component
//public class HibernateSchemaInitializer implements CommandLineRunner {
//
//    private final DataSource dataSource;
//    private final List<String> tenantSchemas = List.of("tenant_1", "tenant_2");
//
//    public HibernateSchemaInitializer(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    // 애플리케이션이 시작된 후 실행
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            System.out.println("스키마 삭제 및 재생성 작업 시작");
//
//            // 스키마 삭제 및 재생성
//            dropAndCreateSchema("PUBLIC");
//            dropTenantSchemas();
//
//            // Hibernate 테이블 생성 로직 호출
//            createHibernateTables();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("스키마 초기화 중 오류 발생: " + e.getMessage(), e);
//        }
//    }
//
//    // 스키마 삭제 및 생성
//    private void dropAndCreateSchema(String schemaName) throws SQLException {
//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement()) {
//
//            // 스키마 삭제
//            String dropSchemaSQL = "DROP SCHEMA IF EXISTS " + schemaName + " CASCADE";
//            stmt.execute(dropSchemaSQL);
//
//            // 스키마 생성
//            String createSchemaSQL = "CREATE SCHEMA " + schemaName;
//            stmt.execute(createSchemaSQL);
//
//            System.out.println(schemaName.toUpperCase() + " 스키마 삭제 및 재생성 완료.");
//        }
//    }
//
//    // 테넌트 스키마 삭제 및 생성
//    private void dropTenantSchemas() throws SQLException {
//        for (String tenant : tenantSchemas) {
//            dropAndCreateSchema(tenant);
//        }
//    }
//
//    // Hibernate 테이블 생성 실행
//    private void createHibernateTables() {
//        // SchemaExport를 사용하여 수동으로 테이블 생성
//        SchemaExport schemaExport = new SchemaExport();
//        schemaExport.setHaltOnError(true); // 에러 발생 시 멈춤
//        schemaExport.setFormat(true); // SQL 포맷 설정
////        schemaExport.execute(EnumSet.of(TargetType.DATABASE), null); // 메타데이터 전달 필요
//
//        System.out.println("Hibernate 테이블 생성 완료.");
//    }
//}