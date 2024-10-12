package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrpDTO {

    private Long id; // PK

    private Long mpsId; // 관련된 MPS (Mps 엔티티와 연관)

    private Long workcenterId; // 작업장 (WorkCenter 엔티티와 연관)

    private Double requiredCapacity; // 필요한 작업 시간 (시간 단위)

    private Double availableCapacity; // 가용 작업 시간 (시간 단위)

    private Double capacityLoadPercentage; // 작업장 부하율 (%)

    private String capacityStatus; // 능력 상태 (정상, 과부하, 여유)

    private String remarks; // 추가 설명 또는 비고
}
