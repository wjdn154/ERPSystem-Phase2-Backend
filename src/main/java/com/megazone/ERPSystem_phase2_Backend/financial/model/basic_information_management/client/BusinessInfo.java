package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 업태 및 종목 테이블
@Entity(name = "client_business_info")
@Table(name = "client_business_info")
@Getter
@Setter
public class BusinessInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessType; // 업태

    private String businessItem; // 종목
}
