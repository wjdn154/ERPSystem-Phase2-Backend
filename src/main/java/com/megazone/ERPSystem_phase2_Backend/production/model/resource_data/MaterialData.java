package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.mrp.MaterialInputStatus;
import com.megazone.ERPSystem_phase2_Backend.production.model.mrp.bom.StandardBomMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardousMaterialType;
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

@Entity(name = "production_materialData")
@Table(name = "production_materialData")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "substituteMaterial")
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

    private HazardousMaterialType hazardousMaterialType;     //유해물질

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;                         //품목 테이블 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Client supplier;                         // 공급자 / 외주 작업을 수행하는 공급업체(supplier) from 회계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_input_status_id", nullable = false)
    private MaterialInputStatus materialInputStatus;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StandardBomMaterial> bomMaterials = new ArrayList<>();

//    @OneToMany(mappedBy = "substituteMaterial", fetch = FetchType.LAZY)
//    private List<MaterialData> parent;        //자기참조 부모값? fk값이 들어감? db에는 컬럼이 안생기긴 했음.
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "substituteMaterial_id")
//    private MaterialData substituteMaterial;      //대체자재


}
