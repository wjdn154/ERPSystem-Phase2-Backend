package com.megazone.ERPSystem_phase2_Backend.production.model.mrp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "material_input_status")
@Table(name = "mrp_material_input_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInputStatus {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String code; // 실자재투입현황 코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String name; // 현황등록명 (자동기입? 일자-00제품-00공정-공장-작업장명-설비)

    // 일자 및 시간

    // 제품 Product

    // 공정 단계

    // 자재 리스트

    // 작업장 (공장 연관 포함)

    // 설비 ( 연관 작업장에서 연결 )
}
