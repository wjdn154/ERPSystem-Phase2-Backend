package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.CompletedProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.enums.QualityInspectionType;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**품질검사
 *특정 작업실적에 대해 수행되는 품질검사 단위 전체를 관리하는 엔티티.
 * 품질검사 전체 프로세스의 진행 상태나 결과를 총괄적으로 추적함.
 * 검사 대상이 되는 제품들의 묶음(inspectedProduct 리스트)을 관리하며,
 * 여러개의 제품에 대해 개별적으로 진행된 품질검사를 하나의 품질검사 단위로 묶음.
 * ex) 작업실적 100개에 대한 qualityInspection이 생성되면 100개의 제품이 각강 inspectedProduct로 검사됨.
 * 이때 qualityInspection은 100개의 제품에 대한 품질검사 프로세스 전체를 대표하고
 * 각 inspectedProduct는 100개의 제품 개별에 대한 검사 결과를 나타냄
 *
 * */
@Entity(name = "quality_control_quality_inspection")
@Table(name = "quality_control_quality_inspection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false)
    private String inspectionCode; // 지정검사코드 (식별자와 별도의 지정코드)

    @Column(nullable = false)
    private String inspectionName; // 품질 검사 명

    private String description; // 검사 설명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QualityInspectionType qualityInspectionType;    //품질 검사 유형

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;     //검사 대상 품목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_performance_id", nullable = false)
    private WorkPerformance workPerformance;    //검사 대상이 되는 작업실적(수량 가져오기)

    //개별 제품의 검사 결과를 관리하는 리스트
    @OneToMany(mappedBy = "qualityInspection", fetch = FetchType.LAZY)
    private List<InspectedProduct> inspectedProducts;    //검사된 개별 제품 리스트


    //통관된 제품을 completeProduct로 관리하는 리스트
    @OneToMany(mappedBy = "qualityInspection" , fetch = FetchType.LAZY)
    private List<CompletedProduct> completedProducts;   //품질검사에 따른 완제품 리스트

}
