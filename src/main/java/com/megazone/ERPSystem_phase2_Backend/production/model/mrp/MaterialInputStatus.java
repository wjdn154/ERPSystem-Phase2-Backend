package com.megazone.ERPSystem_phase2_Backend.production.model.mrp;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "mrp_material_input_status")
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

    @Column(nullable = false)
    private LocalDateTime dateTime; // 일자 및 시간

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product; // 제품과 연관 관계
    @Column(nullable = false)
    private String product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_details_id", nullable = false)
    private ProcessDetails processDetails; // 공정 단계와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workcenter_id", nullable = false)
    private Workcenter workcenter; // 작업장과 연관 관계

    @OneToMany(mappedBy = "materialInputStatus", orphanRemoval = true)
    private List<MaterialData> materials; // 자재 리스트와 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentData equipmentData; // 설비와 연관 관계 (단방향)
}
