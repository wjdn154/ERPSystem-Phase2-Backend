package com.megazone.ERPSystem_phase2_Backend;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SqlInitProperties;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
public class ErpSystemPhase2BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpSystemPhase2BackendApplication.class, args);
		System.out.println("젠킨스 테스트5");
	}

//	@Bean
//	public CommandLineRunner commandLineRunner (ApplicationContext ctx){
//		return args -> {
//			System.out.println("Spring 애플리케이션에 등록된 빈 목록:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//		};
//	}
}
