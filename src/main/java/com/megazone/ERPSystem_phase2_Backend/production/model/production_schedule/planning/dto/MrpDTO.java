package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MrpDTO {

    private Long id; // 고유식별자

    private String code; // MRP코드

    private String name; // MRP명

    private String remarks; // MRP 설명

    private Boolean isActive; // 사용 여부

    private Long mpsId; // MPS와 연관 관계 설정

    private Long materialDataId; // 자재와 연관 관계 설정

    private BigDecimal requiredQuantity; // 필요한 총 자재 수량

    private BigDecimal onHandQuantity; // 현재 재고 수량

    private BigDecimal plannedOrderQuantity; // 계획된 발주량

    private LocalDateTime plannedOrderReleaseDate; // 계획된 발주일

    private LocalDateTime plannedOrderReceiptDate; // 계획된 입고일

    private Long standardBomId; // StandardBom과 연관 관계 설정
}
