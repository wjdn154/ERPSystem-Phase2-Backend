package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.RoutingStepDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class searchMpsDTO {
    private Long id; // MPS PK
    private String name; // MPS명
    private LocalDate planDate; // 계획 수립 날짜
    private LocalDate startDate; // 생산 시작일
    private LocalDate endDate; // 생산 완료 예정일
    private String status; // 계획 상태 (계획, 확정, 진행 중, 완료)

    private Long productId; // 생산할 제품 ID
    private String productName; // 생산할 제품명

    private BigDecimal quantity; // 생산 수량
    private String remarks; // 추가 설명 또는 비고

    private Long productionRequestId; // 연관된 생산 의뢰 ID
    private Long saleId; // 관련된 판매 계획 ID

    private List<RoutingStepDetailDTO> routingSteps;

    private List<WorkerDTO> workers; // 작업자 목록
}
