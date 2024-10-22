package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing;

/**
 * 생산 과정에서 제품이나 부품이 거치는 개별 작업 단계 엔티티
 * 각 공정이 수행하는 특정 구체적인 작업 정의
 * 작업장, 작업 시간, 작업자, 사용되는 기계 및 도구, 품질 기준 등이 포함
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.MaterialInputStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "process_routing_process_details")
@Table(name = "process_routing_process_details", indexes = {
        @Index(name = "idx_process_code", columnList = "process_code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

//    @OneToMany(mappedBy = "processDetails", fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Workcenter> workcenters = new ArrayList<>(); // 연관 (공정수행) 작업장 목록

//    @OneToMany(mappedBy = "processDetails", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<RoutingStep> routingSteps = new ArrayList<>(); // 연관 RoutingStep 목록

    @OneToMany(mappedBy = "processDetails")
    private List<MaterialInputStatus>  materialInputStatus;

}
