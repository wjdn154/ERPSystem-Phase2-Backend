package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.production.model.material_requirements_planning.bom.StandardBomMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.HazardousMaterialType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* 자재 정보 관리
* */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "substituteMaterial")
public class MaterialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               //pk

    @Column(nullable = false)
    private String materialName;     //자재 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType materialType;     //자재 테이블 참조. 자재유형  enum


    //private HazardousMaterialType hazardousMaterialType;     //유해물질

    @OneToMany(mappedBy = "substituteMaterial", fetch = FetchType.LAZY)
    private List<MaterialData> parent;        //자기참조 부모값? fk값이 들어감? db에는 컬럼이 안생기긴 했음.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substituteMaterial_id")
    private MaterialData substituteMaterial;      //대체자재

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StandardBomMaterial> bomMaterials = new ArrayList<>();


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private Vendor supplier;     // 공급자 / 외주 작업을 수행하는 공급업체(supplier) from 회계

}
