package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequestTestDTO {
    private String code; // 창고 코드
    private String name; // 창고명
    private WarehouseType warehouseType; // 구분 (창고, 공장, 공장(외주비관리) 등)
    private Long processDetailsId; // 선택한 생산공정의 ID
    private List<WarehouseHierarchyGroupDTO> hierarchyGroups; // 창고 계층 그룹들의 ID 리스트
    private Boolean isActive; // 사용 여부 (사용, 미사용)

    public Warehouse mapToEntity(ProcessDetails processDetail, List<WarehouseHierarchyGroup> hierarchyGroups) {
        return Warehouse.builder()
                .code(this.code)
                .name(this.name)
                .warehouseType(this.warehouseType)
                .processDetail(processDetail)
                .isActive(this.isActive)
                .warehouseHierarchyGroup(hierarchyGroups)
                .build();
    }
}
