package com.megazone.ERPSystem_phase2_Backend.production.model.bom_and_routing;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Routing 정보 관리 테이블 : 생산 과정에서 공정 간의 흐름을 정의
 * 제조 과정에서 어떤 공정을 어떤 순서로 거칠지 명시
 * "재료 준비 -> 절단 -> 용접 -> 검사 -> 포장"과 같은 일련의 공정 순서
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

    @ManyToMany(mappedBy = "process", fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<ProcessDetails> process; // 공정
}
