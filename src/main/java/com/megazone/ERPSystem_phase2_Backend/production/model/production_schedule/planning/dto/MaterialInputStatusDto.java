package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListMaterialDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.MaterialDataShowDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialInputStatusDto {

    private Long id; // 고유식별자

    private String name; // 현황등록명 (자동 생성 가능: 일자-제품-공정-작업장-설비 등)

    private LocalDateTime dateTime; // 일자 및 시간

    private List<MaterialDataShowDTO> materialDataIds; // 자재와 연관 관계

    private Long productionOrderId; // 작업지시와 연관 관계

    private Long processDetailsId; // 공정 단계와 연관 관계

    private Long workcenterId; // 작업장과 연관 관계

    private Long equipmentDataId; // 설비와 연관 관계 (필요 시)

    private BigDecimal quantityConsumed; // 소비된 자재 수량

    private String unitOfMeasure; // 자재의 단위

    private String remarks; // 추가 설명 또는 비고
}
