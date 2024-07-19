package com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회사 대표자 정보 테이블
 * 회사 등록시 필요한 대표자 데이터 테이블
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false, unique = true)
    private String name; // 대표자명

    @Column(nullable = false, unique = true)
    private String idNumber; // 대표자 주민번호

    @Column(nullable = false)
    private Boolean isForeign ; // 대표자 외국인여부
}