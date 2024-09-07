package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import jakarta.persistence.*;
import lombok.*;


/**
 * 품목 테이블
 * 품목에 대한 정보가 있는 테이블 - 품목 등록 시 사용
 */
@Entity(name = "product")
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 품목 코드
    @Column(nullable = false)
    private String code;

    // 품목 그룹 코드 참조
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_group_id", referencedColumnName = "id")
    private ProductGroup productGroup;

    // 생산 라우팅 매핑
    @ManyToOne
    @JoinColumn(name = "production_routing_id")
    private ProductionRouting productionRouting;

    // 품목구분 (Enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

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

    // 폼목 사용 여부
    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

    public void deactivate() {
        this.isActive = false;
    }

    public void reactivate() {
        this.isActive = true;
    }
}