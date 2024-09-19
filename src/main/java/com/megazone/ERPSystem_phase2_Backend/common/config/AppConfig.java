package com.megazone.ERPSystem_phase2_Backend.common.config;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SqlInitProperties;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}