// 플러그인 설정: Java와 Spring Boot 관련 플러그인을 포함
plugins {
	java // Java 플러그인 사용
	id("org.springframework.boot") version "3.3.1" // Spring Boot 플러그인 사용
	id("io.spring.dependency-management") version "1.1.5" // Spring Dependency Management 플러그인 사용
}

// 프로젝트 기본 정보 설정: 그룹 ID, 버전
group = "com.megazone" // 프로젝트 그룹 ID 설정
version = "0.0.1-SNAPSHOT" // 프로젝트 버전 설정

// Java 컴파일러 설정: 사용할 Java 버전 지정
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(18) // Java 17 버전 사용
	}
}

// 컴파일 설정: 주석 처리 도구를 포함시키는 컴파일 전용 설정 확장
configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get()) // 어노테이션 프로세서 설정 확장
	}
}

// 리포지토리 설정: 의존성을 가져올 Maven 중앙 저장소 지정
repositories {
	mavenCentral() // Maven 중앙 저장소 사용
}

// 의존성 설정: 프로젝트에서 사용할 라이브러리와 도구들
dependencies {
	// 스프링 부트의 JPA 및 웹 스타터 패키지
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA 스타터
	implementation("org.springframework.boot:spring-boot-starter-web") // 웹 스타터
	// Querydsl 관련 라이브러리
	implementation("com.querydsl:querydsl-apt:5.0.0") // Querydsl APT
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta") // Querydsl JPA Jakarta
	implementation("com.querydsl:querydsl-core:5.0.0") // Querydsl Core
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	// 어노테이션 프로세서로 Querydsl, Jakarta API 사용
	annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta") // Querydsl APT 어노테이션 프로세서
	annotationProcessor("jakarta.annotation:jakarta.annotation-api") // Jakarta 어노테이션 API
	annotationProcessor("jakarta.persistence:jakarta.persistence-api") // Jakarta Persistence API
	// Lombok 및 개발 도구
	compileOnly("org.projectlombok:lombok") // Lombok
	developmentOnly("org.springframework.boot:spring-boot-devtools") // Spring Boot DevTools
	annotationProcessor("org.projectlombok:lombok") // Lombok 어노테이션 프로세서
	// 테스트 관련 의존성
	testImplementation("org.springframework.boot:spring-boot-starter-test") // Spring Boot 테스트 스타터
	testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit 플랫폼 런처
	// MySQL JDBC 드라이버
	runtimeOnly("mysql:mysql-connector-java:8.0.30") // MySQL 드라이버
}

// 소스 세트 설정: 생성된 QClass 파일을 메인 소스 디렉토리에 포함
sourceSets {
	main {
		java {
			srcDirs("src/main/java", "build/generated/source/apt/main") // Java 소스 디렉토리 설정
		}
	}
}

// 어노테이션 프로세서로 생성된 파일의 출력 디렉토리 설정
tasks.withType<JavaCompile> {
	options.annotationProcessorGeneratedSourcesDirectory = file("build/generated/source/apt/main") // 어노테이션 프로세서 출력 디렉토리 설정
}

// clean 태스크 설정: QClass 디렉토리 삭제
tasks.named("clean", Delete::class.java) {
	delete(file("build/generated/source/apt/main")) // clean 시 QClass 디렉토리 삭제
}

// 테스트 설정: JUnit 플랫폼 사용
tasks.withType<Test> {
	useJUnitPlatform(); // JUnit 플랫폼 사용
}