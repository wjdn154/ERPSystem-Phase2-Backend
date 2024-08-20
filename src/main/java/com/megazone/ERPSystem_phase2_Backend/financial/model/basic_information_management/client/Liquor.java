package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 주류 코드 테이블
@Entity(name = "client_liquor")
@Table(name = "client_liquor")
@Getter
@Setter
public class Liquor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // 주류 코드

    @Column(nullable = false)
    private String name; // 주류 코드 이름 (예: 유흥음식점, 일반소매점 등)
}