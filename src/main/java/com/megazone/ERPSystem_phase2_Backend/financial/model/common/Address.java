package com.megazone.ERPSystem_phase2_Backend.financial.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 주소 테이블
@Entity(name = "financial_address")
@Table(name = "financial_address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postalCode; // 우편번호

    private String roadAddress; // 도로명 주소

    private String detailedAddress; // 상세 주소
}
