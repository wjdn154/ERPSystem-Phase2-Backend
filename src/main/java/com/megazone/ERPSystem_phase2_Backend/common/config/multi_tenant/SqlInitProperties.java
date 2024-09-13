package com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * yml에 설정한 spring.sql.init 의 데이터를 주입받는 클래스.
 */
@Setter
@Getter
@Component
@Primary
@ConfigurationProperties(prefix = "spring.sql.init")
public class SqlInitProperties {
    // SQL 파일 경로 목록을 저장하는 리스트
    private List<String> dataLocations;
}