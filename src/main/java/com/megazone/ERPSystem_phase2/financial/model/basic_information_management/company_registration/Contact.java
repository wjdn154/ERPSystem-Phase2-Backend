package com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 연락처 정보 테이블
 * 회사 등록 시 필요한 연락처 데이터 테이블
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String businessPhone; // 사업장 전화번호

    private String fax; // 팩스번호
}