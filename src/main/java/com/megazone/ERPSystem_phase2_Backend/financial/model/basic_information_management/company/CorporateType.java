package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 법인구분 테이블
 * 법인구분 데이터 테이블
 */
@Entity(name = "company_corporate_type")
@Table(name = "company_corporate_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String code; // 법인구분 코드

    @Column(nullable = false)
    private String type; // 법인구분

    private String description; // 법인구분 설명

    public CorporateType(String code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }
}