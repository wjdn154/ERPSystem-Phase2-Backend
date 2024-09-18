package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.enums.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTransferResponseListDTO {

    private Long id;  // 창고 이동 ID
    private String transferNumber;  // 이동번호 (이동 날짜 + " - " + 이동넘버)
    private String sendingWarehouseName;  // 보내는 창고명
    private String receivingWarehouseName;  // 받는 창고명
    private String firstProductName;  // 첫 번째 품목명
    private String additionalProducts;
    private Long totalQuantity;  // 총 수량
    private TransferStatus status;

}
