package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 법인종류별 구분 테이블
 * 법인종류별 구분 데이터 테이블
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateKind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String kind; // 법인종류

    private String description; // 법인종류 설명
}