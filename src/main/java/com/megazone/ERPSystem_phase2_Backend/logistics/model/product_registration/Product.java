package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration;

import jakarta.persistence.*;
import lombok.*;


/**
 * 품목 테이블
 * 품목에 대한 정보가 있는 테이블 - 품목 등록 시 사용
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 품목 코드
    @Column(nullable = false)
    private String code;

    // 품목 그룹 코드 참조
    @Column(nullable = false)
    private Long groupId;

    // 생성공정 코드 참조
    @Column(nullable = false)
    private Long productionProcessId;

    // 품목구분 (Enum)
    @Column(nullable = false)
    private Long productTypeId;

    // 입고 단가
    @Column(nullable = false)
    private Double purchasePrice;

    // 출고 단가
    @Column(nullable = false)
    private Double salesPrice;

    // 품목명
    @Column(nullable = false)
    private String name;

    // 규격
    @Column(nullable = false)
    private String standard;

    // 단위
    @Column(nullable = false)
    private String unit;

}