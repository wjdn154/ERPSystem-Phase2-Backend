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
public class WarehouseTransferResponseDTO {
    private Long id; // 생성된 창고 이동 ID
    private LocalDate transferDate; // 창고 이동 날짜
    private String transferNumber; // 생성된 이동 번호
    private String sendingWarehouseName; // 보내는 창고 이름
    private String receivingWarehouseName; // 받는 창고 이름
    private String employeeName; // 담당자 이름
    private String comment; // 창고 이동 관련 코멘트
    private TransferStatus status; // 전표 상태 (미확인, 확인 등)
    private List<WarehouseTransferProductResponseDTO> products; // 이동 품목 리스트
}
