package com.megazone.ERPSystem_phase2_Backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    /**
     * CORS 설정을 위한 WebMvcConfigurer 빈을 생성함
     * @return WebMvcConfigurer를 반환함
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * CORS 매핑을 추가함
             * @param registry CORS 설정을 등록하는 객체
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // "/api/**" 패턴에 대해 CORS 허용
                        .allowedOrigins("http://localhost:3000") // 허용할 도메인을 "http://localhost:3000"으로 제한함
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드를 지정함
                        .allowedHeaders("*") // 모든 헤더를 허용함
                        .allowCredentials(true); // 자격 증명을 허용함 (쿠키 등을 허용함)
            }
        };
    }
}