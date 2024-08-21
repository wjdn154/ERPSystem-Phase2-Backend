package com.megazone.ERPSystem_phase2_Backend.financial.model.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 연락처 정보 테이블
@Entity(name = "financial_contact")
@Table(name = "financial_contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String phone; // 사업장 전화번호

    private String fax; // 팩스번호

    public Contact(String phone, String fax) {
        this.phone = phone;
        this.fax = fax;
    }
}