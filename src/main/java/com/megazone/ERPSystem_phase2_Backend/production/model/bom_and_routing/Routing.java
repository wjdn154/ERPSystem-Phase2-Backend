package com.megazone.ERPSystem_phase2_Backend.production.model.bom_and_routing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Routing 정보 관리 테이블 : 각 생산품목에 대해 공정 경로를 정의하고 관리하여
 * 생산 공정의 효율성을 극대화하기 위해 사용
 * 제품을 만들기 위해 어떤 작업을 어떤 순서로 해야 하는지 기록하고 관리
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @Column(nullable = false)
    private String code; // Routing 지정코드

    @Column(nullable = false)
    private String name; // Routing 이름

    @Column(nullable = false)
    private String type; // 완제품, 반제품 구분

    @Column(nullable = false)
    private String description; // Routing 설명

    @Column(nullable = false)
    private boolean isStandard; // 표준 여부

    @Column(nullable = false)
    private boolean isActive; // 사용 여부
}
