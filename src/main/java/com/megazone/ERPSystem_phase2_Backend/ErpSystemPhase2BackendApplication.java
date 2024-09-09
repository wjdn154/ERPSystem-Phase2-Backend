package com.megazone.ERPSystem_phase2_Backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
public class ErpSystemPhase2BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ErpSystemPhase2BackendApplication.class, args);
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
