package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "planning_crp")
@Table(name = "planning_crp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crp_id", nullable = false)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "mps_id", nullable = false)
    private Mps mps; // 관련된 MPS (Mps 엔티티와 연관)

    @ManyToOne
    @JoinColumn(name = "work_center_id", nullable = false)
    private Workcenter workcenter; // 작업장 (WorkCenter 엔티티와 연관)

    @Column(nullable = false)
    private Double requiredCapacity; // 필요한 작업 시간 (시간 단위)

    @Column(nullable = false)
    private Double availableCapacity; // 가용 작업 시간 (시간 단위)

    @Column(nullable = false)
    private Double capacityLoadPercentage; // 작업장 부하율 (%)

    @Column(nullable = false)
    private String capacityStatus; // 능력 상태 (정상, 과부하, 여유)

    @Column(nullable = true)
    private String remarks; // 추가 설명 또는 비고

    // 연관 엔티티에서 활용:
    // - Mps 엔티티에서 CRP를 참조하여 생산계획의 실행 가능성을 검토할 수 있음.
    // - WorkCenter 엔티티에서 CRP를 참조하여 작업장별 부하를 관리하고 스케줄링에 활용.
}
