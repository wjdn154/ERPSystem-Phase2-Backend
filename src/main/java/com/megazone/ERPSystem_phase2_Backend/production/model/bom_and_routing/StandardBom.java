package com.megazone.ERPSystem_phase2_Backend.production.model.bom_and_routing;

/*
    제품과 부품 간의 관계를 관리하며, 각 부품의 수량, 손실율, 유효기간 등을 포함한
    BOM(Bill of Materials) 정보를 저장하는 테이블.

    BOM 정전개(Forward Explosion)와 역전개(Backward Explosion) 모두 수행 (별도 테이블X)
    - BOM 정전개 (Forward Explosion)
      특정 품목을 기준으로 그 하위 품목들(부품들)을 조회하는 방법
    - BOM 역전개 (Backward Explosion)
      특정 부품이 어느 상위 품목들(제품들)에 포함되는지를 조회하는 방법
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardBom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @Column(nullable = false)
    private String code; // BOM 지정코드

    @Column(nullable = true)
    private String description; // 설명

    @Column(nullable = false)
    private Double lossRate; // LOSS(%): 손실율

    @Column(nullable = true)
    private Boolean isSubcontracting; // 하청여부

    @Column(nullable = true)
    private Boolean outsourcingType; // 외주구분

    @Column(nullable = false)
    private LocalDate startDate; // Bom 유효시작일

    @Column(nullable = false)
    private LocalDate expiredDate; // Bom 유효종료일

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

    @Column(nullable = true)
    private String remarks; // 비고


    @Column(nullable = false)
    private BigDecimal quantity; // 품목별 자재 수량

//    @Column(nullable = false)
//    private final String childProductId; // 자식 품목 엔티티 from 물류 Item

//    @Column(nullable = false)
//    private final String materialId; // 자재 엔티티 (eager?)
}
