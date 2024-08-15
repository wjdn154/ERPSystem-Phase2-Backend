package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.production.model.material_requirements_planning.bom.StandardBomMaterial;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               //pk

    @Column(nullable = false)
    private String materialName;     //자재 이름

    @Column(nullable = false)
    private String materialType;     //자재유형

    @Column(nullable = false)
    private Long inventoryQuantity;  //재고수량

    @Column(nullable = false)
    private Date receiveDate;        //입고날짜

    @Column(nullable = false)
    private Date releaseDate;        //출고날짜

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StandardBomMaterial> bomMaterials = new ArrayList<>();


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private Vendor supplier;     // 공급자 / 외주 작업을 수행하는 공급업체(supplier) from 회계
}
