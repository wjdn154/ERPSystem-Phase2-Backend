package com.megazone.ERPSystem_phase2_Backend.common.config;

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
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://127.0.0.1:3000",
                                "http://localhost:3000",
                                "http://15.165.212.208:3000",
                                "https://15.165.212.208:3000",
                                "http://omz.kro.kr",
                                "https://omz.kro.kr"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}