package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "human_resources_code")
@Table(name = "human_resources_code")
/**
 * 급여 기초 코드 최상위
 */
public class HumanResourcesMiddleAccountSubject {
    @Id
    @GeneratedValue
    @Unique
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 급여 최상위 계정코드

    @Column(nullable = false, unique = true)
    private String codeRules; // 급여 카테고리 코드식별자

    @Column(nullable = false, unique = true)
    private String name; // 급여 최상위 계정이름
}
