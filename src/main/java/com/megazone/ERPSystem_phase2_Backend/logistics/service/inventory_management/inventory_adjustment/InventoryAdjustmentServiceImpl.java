package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.InventoryHistory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.AdjustmentRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_history.InventoryHistoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_location_management.WarehouseLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryAdjustmentServiceImpl implements InventoryAdjustmentService {

    private final InventoryRepository inventoryRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void adjustInventory(AdjustmentRequestDTO adjustmentRequestDTO) {
        Inventory inventory = inventoryRepository.findById(adjustmentRequestDTO.getInventoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 재고가 존재하지 않습니다."));

        if (adjustmentRequestDTO.getAdjustmentType().equals("재고조정")) {
            handleInventoryAdjustment(inventory, adjustmentRequestDTO);
        } else if (adjustmentRequestDTO.getAdjustmentType().equals("위치이동")) {
            handleLocationTransfer(inventory, adjustmentRequestDTO);
        }

        InventoryHistory history = createInventoryHistory(inventory, adjustmentRequestDTO);
        inventoryHistoryRepository.save(history);
    }

    private void handleInventoryAdjustment(Inventory inventory, AdjustmentRequestDTO adjustmentRequestDTO) {
        inventory.updateQuantity(adjustmentRequestDTO.getQuantity());
        inventoryRepository.save(inventory);
    }

    private void handleLocationTransfer(Inventory inventory, AdjustmentRequestDTO adjustmentRequestDTO) {
        // 새로운 위치 정보를 조회하여 재고의 위치를 업데이트
        WarehouseLocation newLocation = warehouseLocationRepository.findById(adjustmentRequestDTO.getReceiveWarehouseLocationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 위치가 존재하지 않습니다."));
        inventory.updateLocation(newLocation);
        inventoryRepository.save(inventory);
    }

    private InventoryHistory createInventoryHistory(Inventory inventory, AdjustmentRequestDTO adjustmentRequestDTO) {
        Employee employee = employeeRepository.findById(adjustmentRequestDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("작업자 정보가 존재하지 않습니다."));

        return InventoryHistory.builder()
                .inventory(inventory)
                .warehouse(inventory.getWarehouse())
                .sendWarehouseLocation(inventory.getWarehouseLocation())
                .receiveWarehouseLocation(
                        adjustmentRequestDTO.getAdjustmentType().equals("위치이동") ?
                                warehouseLocationRepository.findById(adjustmentRequestDTO.getReceiveWarehouseLocationId()).orElse(null) : null
                )
                .employee(employee)
                .workDate(adjustmentRequestDTO.getAdjustmentDate())
                .slipDate(LocalDate.now())
                .workNumber(generateWorkNumber(adjustmentRequestDTO.getAdjustmentDate()))
                .slipNumber(generateSlipNumber(adjustmentRequestDTO.getAdjustmentDate()))
                .workType(adjustmentRequestDTO.getAdjustmentType())
                .receiveInventoryNumber(inventory.getInventoryNumber())
                .workQuantity(adjustmentRequestDTO.getQuantity())
                .summary(adjustmentRequestDTO.getSummary())
                .build();
    }

    private Long generateWorkNumber(LocalDate workDate) {
        Long maxWorkNumber = inventoryHistoryRepository.findMaxWorkNumberByDate(workDate);
        return (maxWorkNumber == null) ? 1L : maxWorkNumber + 1;
    }

    private Long generateSlipNumber(LocalDate slipDate) {
        Long maxSlipNumber = inventoryHistoryRepository.findMaxSlipNumberByDate(slipDate);
        return (maxSlipNumber == null) ? 1L : maxSlipNumber + 1;
    }
}
