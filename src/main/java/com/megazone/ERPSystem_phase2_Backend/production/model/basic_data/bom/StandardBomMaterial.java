package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 자재와 품목(Product) 간의 관계를 정의합니다. 이 엔티티는 특정 BOM과 연결된 자재 정보와 그 수량을 관리합니다.
 * StandardBom은 자재 정보를 직접 관리하지 않고, StandardBomMaterial을 통해 간접적으로 관리합니다.
 *
 * StandardBom: BOM의 기본 정보와 제품 간의 계층 구조를 관리.
 * StandardBomMaterial: 각 BOM에 필요한 자재와 품목 간의 관계 및 수량을 관리.
 */

@Entity(name = "bom_standard_bom_material")
@Table(name = "bom_standard_bom_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardBomMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_bom_id", nullable = false)
    private StandardBom bom; // 특정 BOM 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialData material; // 자재 참조

    @Column(nullable = false)
    private Long quantity; // 해당 자재의 필요 수량
}
