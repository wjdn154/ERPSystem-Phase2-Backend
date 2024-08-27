package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회사 대표자 정보 테이블
 * 회사 등록시 필요한 대표자 데이터 테이블
 */
@Entity(name = "company_representative")
@Table(name = "company_representative")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String name; // 대표자명

    @Column(nullable = false)
    private String idNumber; // 대표자 주민번호

    @Column(nullable = false)
    private Boolean isForeign ; // 대표자 외국인여부

    public Representative(String name, String idNumber, Boolean isForeign) {
        this.name = name;
        this.idNumber = idNumber;
        this.isForeign = isForeign;
    }
}