package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.enums.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferRequestDTO {
    private LocalDate transferDate; // 창고 이동 날짜
    private Long sendingWarehouseId; // 보내는 창고 ID
    private Long receivingWarehouseId; // 받는 창고 ID
    private Long employeeId; // 담당자 ID
    private String comment; // 창고 이동 관련 코멘트 (선택 사항)
    private TransferStatus status; // 전표 상태 (미확인, 확인 등)
    private List<WarehouseTransferProductRequestDTO> products; // 이동 품목 리스트
}
