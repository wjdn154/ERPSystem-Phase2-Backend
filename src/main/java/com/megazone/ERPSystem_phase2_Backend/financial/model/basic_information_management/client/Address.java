package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 주소 테이블
@Entity(name = "client_address")
@Table(name = "client_address")
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
