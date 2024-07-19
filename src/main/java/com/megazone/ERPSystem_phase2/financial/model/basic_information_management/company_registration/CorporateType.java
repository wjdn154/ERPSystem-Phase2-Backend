package com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 법인구분 테이블
 * 법인구분 데이터 테이블
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String type; // 법인구분

    private String description; // 법인구분 설명
}