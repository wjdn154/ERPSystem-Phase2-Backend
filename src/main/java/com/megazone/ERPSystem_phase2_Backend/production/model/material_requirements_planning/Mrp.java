package com.megazone.ERPSystem_phase2_Backend.production.model.material_requirements_planning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 총 소요량 = 공정별 작업계획수량 × 해당공정소요수량 + (해당공정소요수량 × 로스율)
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mrp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // MRP코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // MRP명

    @Column(nullable = true)
    private String remarks; // MRP 설명

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

//    @ManyToMany
//    @JoinTable
//    private List<MaterialData>materialDataList; // 자재 개별데이터 리스트
}
