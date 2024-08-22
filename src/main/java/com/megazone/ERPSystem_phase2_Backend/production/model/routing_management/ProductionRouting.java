package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Routing 정보 관리 테이블 : 생산 과정에서 공정 간의 흐름을 정의
 * 제조 과정에서 어떤 공정을 어떤 순서로 거칠지 명시
 * "재료 준비 -> 절단 -> 용접 -> 검사 -> 포장"과 같은 일련의 공정 순서
 */

@Entity(name = "routing_management_production_routing")
@Table(name = "routing_management_production_routing", indexes = {
        @Index(name = "idx_production_routing_code", columnList = "production_routing_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionRouting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @Column(name = "production_routing_code", nullable = false, unique = true)
    private String code; // Routing 지정코드

    @Column(nullable = false)
    private String name; // Routing 이름

    @Column(nullable = false)
    private String description; // Routing 설명

    @Column(nullable = false)
    private boolean isStandard; // 표준 여부

    @Column(nullable = false)
    private boolean isActive; // 사용 여부

    @OneToMany(mappedBy = "productionRouting", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepOrder ASC") // stepOrder 필드를 기준으로 오름차순 정렬
    private List<RoutingStep> routingSteps; // 연관 RoutingStep 목록

//    @OneToMany(mappedBy = "routing")
//    private List<Product> products;

//    // Product 엔티티에서
//    @ManyToOne
//    @JoinColumn(name = "routing_id")
//    private Routing routing;

}
