//package com.megazone.ERPSystem_phase2_Backend.common.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import javax.sql.DataSource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataSourceConfig {
//
//    private final SecretManagerConfig secretManagerConfig;
//
//    @Bean
//    public DataSource dataSource() {
//        DatabaseCredentials credentials = secretManagerConfig.getSecret();
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://" + credentials.getUrl());
//        dataSource.setUsername(credentials.getUsername());
//        dataSource.setPassword(credentials.getPassword());
//
//        return dataSource;
//    }
//}