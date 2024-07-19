package com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회사 및 거래처 등록의 업종형태
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String name; // 회사의 업종형태
}