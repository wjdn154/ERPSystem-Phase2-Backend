package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.HierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.hierarchy_group.WarehouseHierarchyGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseRequestTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.test.WarehouseResponseTestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group.HierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse_hierarchy_group.WarehouseHierarchyGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection.InventoryInspectionRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer.WarehouseTransferRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_request.PurchaseRequestRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management.ShipmentRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_location_management.WarehouseLocationRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final HierarchyGroupRepository hierarchyGroupRepository;
    private final WarehouseHierarchyGroupRepository warehouseHierarchyGroupRepository;
    private final ProcessDetailsRepository processDetailsRepository;
    private final InventoryRepository inventoryRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final InventoryInspectionRepository inventoryInspectionRepository;
    private final ShipmentRepository shipmentRepository;


    @Override
    public List<WarehouseResponseListDTO> getWarehouseList() {
        return warehouseRepository.findWarehouseList();
    }

    @Override
    public WarehouseResponseTestDTO getWarehouseDetail(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 창고를 찾을 수 없습니다."));

        return WarehouseResponseTestDTO.mapToDTO(warehouse);
    }

    @Override
    public WarehouseResponseTestDTO createWarehouse(WarehouseRequestTestDTO requestDTO) {
        ProcessDetails processDetail = null;
        if (requestDTO.getWarehouseType() == WarehouseType.FACTORY && requestDTO.getProcessDetailId() != null) {
            processDetail = processDetailsRepository.findById(requestDTO.getProcessDetailId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 생산 공정입니다."));
        }

        List<WarehouseHierarchyGroup> hierarchyGroups = new ArrayList<>();
        if (requestDTO.getHierarchyGroups() != null && !requestDTO.getHierarchyGroups().isEmpty()) {
            hierarchyGroups = requestDTO.getHierarchyGroups().stream()
                    .map(dto -> warehouseHierarchyGroupRepository.findById(dto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계층 그룹입니다.")))
                    .collect(Collectors.toList());
        }

        Warehouse newWarehouse = requestDTO.mapToEntity(processDetail, hierarchyGroups);

        Warehouse savedWarehouse = warehouseRepository.save(newWarehouse);

        return WarehouseResponseTestDTO.mapToDTO(savedWarehouse);
    }

    @Override
    public WarehouseResponseTestDTO updateWarehouse(Long warehouseId, WarehouseRequestTestDTO requestDTO) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 창고를 찾을 수 없습니다."));

        // 계층 그룹이 입력되지 않은 경우를 처리
        List<WarehouseHierarchyGroup> updatedHierarchyGroups = new ArrayList<>();
        if (requestDTO.getHierarchyGroups() != null && !requestDTO.getHierarchyGroups().isEmpty()) {
            updatedHierarchyGroups = requestDTO.getHierarchyGroups().stream()
                    .map(dto -> {
                        HierarchyGroup hierarchyGroup = hierarchyGroupRepository.findById(dto.getId())
                                .orElseThrow(() -> new IllegalArgumentException("해당 계층 그룹을 찾을 수 없습니다."));
                        return WarehouseHierarchyGroup.builder()
                                .warehouse(existingWarehouse)
                                .hierarchyGroup(hierarchyGroup)
                                .build();
                    })
                    .collect(Collectors.toList());
        }

        ProcessDetails processDetail = null;
        if (requestDTO.getWarehouseType() == WarehouseType.FACTORY && requestDTO.getProcessDetailId() != null) {
            processDetail = processDetailsRepository.findById(requestDTO.getProcessDetailId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 생산 공정을 찾을 수 없습니다."));
        }

        Warehouse updatedWarehouse = Warehouse.builder()
                .id(existingWarehouse.getId())
                .code(requestDTO.getCode())
                .name(requestDTO.getName())
                .warehouseType(requestDTO.getWarehouseType())
                .isActive(requestDTO.getIsActive())
                .processDetail(processDetail)
                .warehouseHierarchyGroup(updatedHierarchyGroups)
                .build();

        Warehouse savedWarehouse = warehouseRepository.save(updatedWarehouse);

        return WarehouseResponseTestDTO.mapToDTO(savedWarehouse);
    }


    @Override
    public String deleteWarehouse(Long id) {
        return warehouseRepository.findById(id)
                .map(warehouse -> {
                    String relatedIssues = getRelatedEntitiesIssues(warehouse);

                    if (!relatedIssues.isEmpty()) {
                        // 연관된 데이터가 있을 때 명확한 예외 메시지 반환
                        throw new IllegalStateException(
                                "삭제 실패: " + relatedIssues
                        );
                    }
                    // 연관된 데이터가 없을 경우 창고 삭제
                    warehouseRepository.delete(warehouse);
                    return warehouse.getName() + "가 삭제되었습니다.";
                })
                .orElse("삭제 실패: 삭제하려는 창고 ID를 찾을 수 없습니다.");
    }

    // 연관된 테이블의 문제를 확인하고 메시지를 생성하는 메서드
    private String getRelatedEntitiesIssues(Warehouse warehouse) {
        StringBuilder issues = new StringBuilder();

        // 각 테이블에서 창고를 참조하는 데이터가 있는지 확인
        if (inventoryRepository.existsByWarehouse(warehouse)) {
            issues.append("재고가 존재합니다. ");
        }
        if (purchaseRequestRepository.existsByReceivingWarehouse(warehouse)) {
            issues.append("해당 창고의 구매 지시가 존재합니다. ");
        }
        if (warehouseLocationRepository.existsByWarehouse(warehouse)) {
            issues.append("해당 창고의 로케이션이 존재합니다. ");
        }
        if (warehouseTransferRepository.existsBySendingWarehouse(warehouse) ||
                warehouseTransferRepository.existsByReceivingWarehouse(warehouse)) {
            issues.append("해당 창고의 창고 이동 전표가 존재합니다. ");
        }
        if (inventoryInspectionRepository.existsByWarehouse(warehouse)) {
            issues.append("해당 창고의 재고 실사 입력이 존재합니다. ");
        }
        if (shipmentRepository.existsByWarehouse(warehouse)) {
            issues.append("해당 창고의 출하전표가 존재합니다. ");
        }

        return issues.toString().trim(); // 최종 문제 메시지 반환
    }
}

