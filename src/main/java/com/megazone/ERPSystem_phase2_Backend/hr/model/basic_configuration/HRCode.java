package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "hr_code")
@Table(name = "hr_code")
public class HRCode {
    @Id
    @GeneratedValue
    @Unique
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 계정코드

    @Column(nullable = false, unique = true)
    private String codeRules; // 계정카테고리 식별자

    @Column(nullable = false, unique = true)
    private String name; // 계정이름

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CodeType codeType; // 코드 카테고리 체계분류
}
