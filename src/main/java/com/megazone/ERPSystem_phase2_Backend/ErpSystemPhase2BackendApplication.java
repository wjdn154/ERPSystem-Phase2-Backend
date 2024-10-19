package com.megazone.ERPSystem_phase2_Backend;

import com.megazone.ERPSystem_phase2_Backend.common.config.DotenvPropertySourceFactory;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SqlInitProperties;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
public class ErpSystemPhase2BackendApplication {
	public static void main(String[] args) {

		SpringApplication.run(ErpSystemPhase2BackendApplication.class, args);
	}
}