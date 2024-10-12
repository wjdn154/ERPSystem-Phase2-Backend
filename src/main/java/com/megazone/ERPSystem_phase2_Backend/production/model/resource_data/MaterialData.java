package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.MaterialInputStatus;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBomMaterial;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
* 자재 정보 관리
* */

@Entity(name = "production_material_data")
@Table(name = "production_material_data")
@AllArgsConstructor
@NoArgsConstructor
@Data
//@ToString(exclude = "substituteMaterial")
public class MaterialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               //pk

    @Column(nullable = false, unique = true)
    private String materialCode;    //자재 코드

    @Column(nullable = false)
    private String materialName;     //자재 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType materialType;       //자재 테이블 참조. 자재유형  enum

    @Column(nullable = false)
    private Long stockQuantity;              //재고 수량

    @Column(nullable = false)
    private BigDecimal purchasePrice;        //구매 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client supplier;                         // 공급자 / 외주 작업을 수행하는 공급업체(supplier) from 회계

    @OneToMany(mappedBy = "materialData", fetch = FetchType.LAZY)
    private List<MaterialHazardous> materialHazardous = new ArrayList<>();     //자재와 유해물질 다대다 중간 엔티티

    @OneToMany(mappedBy = "materialData", fetch = FetchType.LAZY)          //자재와 품목 다대다 중간 엔티티
    private List<MaterialProduct> materialProducts = new ArrayList<>();

    @OneToMany(mappedBy = "materialData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaterialInputStatus> materialInputStatusList;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StandardBomMaterial> bomMaterials = new ArrayList<>();


}
