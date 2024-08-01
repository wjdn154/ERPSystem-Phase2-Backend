package com.megazone.ERPSystem_phase2_Backend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL을 사용하기 위한 설정 클래스
 */
@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager entityManager; // EntityManager를 통한 데이터베이스 연동

    /**
     * JPAQueryFactory 빈 생성 메서드
     *
     * @return JPAQueryFactory 객체
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager); // JPAQueryFactory 객체를 생성하여 반환함
    }
}