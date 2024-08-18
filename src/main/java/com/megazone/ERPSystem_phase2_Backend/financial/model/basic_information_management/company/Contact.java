package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 연락처 정보 테이블
 * 회사 등록 시 필요한 연락처 데이터 테이블
 */
@Entity(name = "company_contact")
@Table(name = "company_contact")
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

    public Contact(String businessPhone, String fax) {
        this.businessPhone = businessPhone;
        this.fax = fax;
    }
}