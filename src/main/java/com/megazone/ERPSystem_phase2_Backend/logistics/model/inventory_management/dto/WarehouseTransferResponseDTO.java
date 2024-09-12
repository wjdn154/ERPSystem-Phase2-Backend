package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferResponseDTO {
    private Long id;                         // 창고 이동 ID
    private String companyName;              // 회사 이름
    private String sendingWarehouseName;     // 발신 창고 이름
    private String receivingWarehouseName;   // 수신 창고 이름
    private String employeeName;             // 직원 이름
    private LocalDateTime transferDate;      // 이동 날짜
    private String status;                   // 상태 (대기, 진행 중, 완료)
    private String comment;                  // 비고사항 (nullable)
    private LocalDateTime createdAt;         // 생성일
    private List<WarehouseTransferProductResponseDTO> products;  // 제품 리스트
}
