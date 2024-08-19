package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// 재무 정보 테이블
@Entity(name = "client_financial_info")
@Table(name = "client_financial_info")
@Getter
@Setter
public class FinancialInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal collateralAmount; // 담보설정액

    private BigDecimal creditLimit; // 여신한도액
}
