package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferProductResponseDTO {
    private Long id; // 품목 ID (고유)
    private String productCode; // 품목 코드
    private String productName; // 품목명
    private Long quantity; // 수량
    private String comment; // 품목 관련 코멘트 (선택 사항)

}
