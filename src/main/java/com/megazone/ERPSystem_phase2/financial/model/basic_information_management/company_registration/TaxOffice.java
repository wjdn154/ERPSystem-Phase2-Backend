package com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 세무서 테이블
 * 회사와 연결될 세무서 정보 데이터 테이블
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String name; // 세무서명

    @Column(nullable = false)
    private String region; // 세무서 지역
}