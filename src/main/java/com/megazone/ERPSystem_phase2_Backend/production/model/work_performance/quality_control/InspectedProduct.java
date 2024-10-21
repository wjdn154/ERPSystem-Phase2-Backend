package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**qualityInspection의 하위 단위로서, 각각의 개별 제품에 대한 검사 결과를 관리함.
 * 검사 대상인 개별 제품의 통과 여부와 불량 유형 및 수량 정보를 포함함.
 * 즉, 개별 제품이 어떤 불량 상태를 가지고 있는지, 검사 결과는 무엇인지를 추적하는 것이 주된 역할임.
 * */
@Data
@Entity(name = "quality_control_inspected_product")
@Table(name = "quality_control_inspected_product")
@AllArgsConstructor
@NoArgsConstructor
public class InspectedProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_inspection_id")
    private QualityInspection qualityInspection;    //품질검사

    @Column(nullable = false)
    private Boolean isPassed;    //개별 제품의 통과 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_category_id")
    private DefectCategory defectCategory;    //불량 유형 (불량인 경우에만 해당)

    private Long defectCount;   //불량 수량(불량 유형이 있는 경우에만 사용)

    //불량 유형에 따른 검사 결과 평가 메소드
    public void evaluateProduct(){
        //불량 유형이 없는 경우 또는 불량 수량이 0인 경우 통과
        if(defectCategory == null || defectCount == 0) this.isPassed = true;
        else this.isPassed = false;
    }
}
