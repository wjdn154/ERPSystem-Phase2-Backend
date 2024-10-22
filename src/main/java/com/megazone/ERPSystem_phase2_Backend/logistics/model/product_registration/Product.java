package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialProduct;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    // 거래처 - N : 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // 품목 그룹 코드 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    // 생산 라우팅 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_routing_id")
    @ToString.Exclude
    private ProcessRouting processRouting;

    // 이미지 파일 경로
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "image_path")
//    private ProductImage imagePath;
    @Column
    private String imagePath;


    // 품목구분 (Enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    // 입고 단가
    @Column(nullable = false)
    private BigDecimal purchasePrice;

    // 출고 단가
    @Column(nullable = false)
    private BigDecimal salesPrice;

    // 품목명
    @Column(nullable = false)
    private String name;

    // 규격
    @Column(nullable = false)
    private String standard;

    // 단위
    @Column(nullable = false)
    private String unit;

    // 비고
    @Column
    private String remarks;

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

    @OneToMany(mappedBy = "product")
    @Builder.Default
    @ToString.Exclude
    private List<PurchaseRequestDetail> purchaseRequestDetails = new ArrayList<>();

    //자재와 품목 다대다 중간 엔티티
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MaterialProduct> materialProducts = new ArrayList<>();
}