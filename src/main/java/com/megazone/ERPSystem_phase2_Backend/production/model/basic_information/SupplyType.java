package com.megazone.ERPSystem_phase2_Backend.production.model.basic_information;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 사급구분 엔티티
 * 사급: 외주업체에 원자재를 공급하는 것
 * - 유상사급: 외주거래시 외주처에 돈을 받고, 생산부품을 공급 -> 부품의 소유권 : 외주처
 * - 무상사급: 외주거래시 외주처에 무상으로 생산부품을 공급 -> 부품의 소유권 : 자사 (구매/자재부 관리)
 */

@Data
@Entity
@Table(name = "supply_type")
public class SupplyType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isPaid; // 유/무상 설정

    // 공장 엔티티 - 공장 코드 from 물류
//    @ManyToOne @JoinColumn(name = "factory_id", nullable = false)
//    private Factory factory;

    // 거래처 엔티티 - 외주처
//    @ManyToOne
//    @JoinColumn(name = "vendor_id", nullable = false)
//    private Vendor vendor; // 외주처 정보

}
