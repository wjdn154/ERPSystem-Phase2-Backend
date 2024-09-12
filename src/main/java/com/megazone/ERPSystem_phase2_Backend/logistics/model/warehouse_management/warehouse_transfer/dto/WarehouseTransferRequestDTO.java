package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferRequestDTO {
    private Long companyId;              // 회사 ID
    private Long sendingWarehouseId;     // 발신 창고 ID
    private Long receivingWarehouseId;   // 수신 창고 ID
    private Long employeeId;             // 직원 ID
    private LocalDateTime transferDate;  // 이동 날짜
    private String status;               // 상태 (대기, 진행 중, 완료)
    private String comment;              // 비고사항 (nullable)
    private List<WarehouseTransferProductRequestDTO> products;  // 제품 리스트

}
