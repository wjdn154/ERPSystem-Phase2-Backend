package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.enums.InspectionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryInspectionResponseListDTO {
    private Long id;  // 실사 항목 ID
    private String inspectionNumber;  // "2024/09/30 - 1" 형식의 전표 번호
    private String inspectionDate;  // 실사 날짜 (2024/09/30)
    private String employeeName;  // 담당자 이름 (예: 김생산)
    private String productSummary;  // 품목 요약 (예: "Z0001", "품목명 외 3건" 등)
    private InspectionStatus status;  // 진행 단계 (예: 실사, 조정)
    private String warehouseName;  // 창고명 (예: 본사창고)
    private Long totalBookQuantity;  // 장부상의 총 수량 (모든 실사 항목의 합계)
    private Long totalActualQuantity;  // 실사된 총 수량 (모든 실사 항목의 합계)
    private Long totalDifferenceQuantity;  // 총 차이 수량 (모든 실사 항목의 합계)
    private List<InventoryInspectionDetailResponseListDTO> details;  // 실사 항목의 상세 정보
}
