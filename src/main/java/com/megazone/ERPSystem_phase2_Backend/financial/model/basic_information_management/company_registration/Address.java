package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주소 정보 테이블
 * 회사등록 시 필요한 주소 데이터 테이블
 */
@Entity(name = "CompanyAddress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유 식별자

    @Column(nullable = false) // 사업장 주소
    private String businessAddress;

    @Column(nullable = false) // 사업장 우편번호
    private String businessPostalCode;

    @Column(nullable = false) // 사업장 동 (예: 대연동)
    private String businessPlace;

    @Column(nullable = false) // 본점 주소
    private String headquarterAddress;

    @Column(nullable = false) // 본점 우편 번호
    private String headquarterPostalCode;

    @Column(nullable = false) // 본점 동
    private String headquarterPlace;
}
