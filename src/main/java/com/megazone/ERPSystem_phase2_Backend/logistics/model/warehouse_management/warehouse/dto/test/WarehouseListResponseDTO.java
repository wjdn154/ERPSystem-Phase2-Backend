package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseListResponseDTO {
    private Long id; // 창고의 ID
    private String code; // 창고 코드
    private String name; // 창고명
    private String warehouseType; // 창고 타입
    private String productionProcess; // 생산공정명 (공장일 때만 사용)
    private Boolean isActive; // 사용 여부

    // 엔티티를 DTO로 변환하는 생성자
    public WarehouseListResponseDTO(Warehouse warehouse) {
        this.id = warehouse.getId(); // 창고의 ID
        this.code = warehouse.getCode(); // 창고 코드
        this.name = warehouse.getName(); // 창고명
        this.warehouseType = warehouse.getWarehouseType().toString(); // 창고 타입 (enum을 String으로 변환)

        // 창고 타입이 공장일 경우에만 생산 공정명 설정
        if (warehouse.getWarehouseType() == WarehouseType.FACTORY) {
            this.productionProcess = warehouse.getProcessDetail() != null ? warehouse.getProcessDetail().getName() : "";
        } else {
            this.productionProcess = ""; // 공장이 아니면 공정명을 빈 값으로 설정
        }

        this.isActive = warehouse.getIsActive(); // 사용 여부
    }
}
