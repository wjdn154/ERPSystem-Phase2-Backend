package com.megazone.ERPSystem_phase2_Backend.common.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

// .env 파일을 읽어서 Environment에 추가하는 EnvironmentPostProcessor
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor, PriorityOrdered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, org.springframework.boot.SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        Map<String, Object> dotenvProperties = new HashMap<>();
        dotenv.entries().forEach(entry -> dotenvProperties.put(entry.getKey(), entry.getValue()));

        MapPropertySource propertySource = new MapPropertySource("dotenvProperties", dotenvProperties);

        // 가장 높은 우선순위로 PropertySource 추가
        environment.getPropertySources().addFirst(propertySource);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}