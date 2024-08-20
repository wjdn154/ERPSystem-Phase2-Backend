package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management;

/**
 * 생산 과정에서 제품이나 부품이 거치는 개별 작업 단계 엔티티
 * 각 공정이 수행하는 특정 구체적인 작업 정의
 * 작업장, 작업 시간, 작업자, 사용되는 기계 및 도구, 품질 기준 등이 포함
 */

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "process_details")
@Table(name = "routing_management_process_details", indexes = {
        @Index(name = "idx_process_code", columnList = "process_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="process_id", nullable = false)
    private Long id; // PK

    @Column(name ="process_code", nullable = false, unique = true)
    private String code; // 공정코드

    @Column(name="process_name", nullable = false)
    private String name; // 공정명

    @Column(name="is_process_outsourced", nullable = false)
    private Boolean isOutsourced; // 사내생산/외주 구분 (true:외주, false:자체생산)

    @Column(name="process_duration", nullable = true)
    private Double duration; // 표준소요시간

    @Column(name="process_cost", nullable = true)
    private BigDecimal cost; // 공정수행비용

    @Column(name="process_defect_rate", nullable = true)
    private Double defectRate; // 평균 불량률

    @Column(name="process_description", nullable = true)
    private String description;    // 공정 설명

    @Column(name="process_is_used", nullable = false)
    private Boolean isUsed; // 사용 여부

    @OneToMany(mappedBy = "processDetails", fetch = FetchType.LAZY)
    private List<Workcenter> workcenters; // 연관 (공정수행) 작업장 목록

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutingStep> routingSteps; // 연관 RoutingStep 목록

//    private List<Warehouse> inputWarehouses; // 해당 공정을 위한 자재가 출고되는 창고명, 창고코드 from 물류

//    private List<Warehouse> outputWarehouses; // 생산품 입고 창고 목록  창고명, 창고코드 from 물류

}
