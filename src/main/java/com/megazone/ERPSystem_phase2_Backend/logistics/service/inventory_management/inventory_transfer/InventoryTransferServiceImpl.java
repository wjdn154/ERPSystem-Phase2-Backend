package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.InventoryHistory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.ReceiveLocationInfo;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.TransferRequestDTO;
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
public class InventoryTransferServiceImpl implements InventoryTransferService {

    private final InventoryRepository inventoryRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void transferInventory(TransferRequestDTO transferRequestDTO) {
        Inventory sendInventory = inventoryRepository.findById(transferRequestDTO.getInventoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 재고를 찾을 수 없습니다."));

        for (ReceiveLocationInfo locationInfo : transferRequestDTO.getReceiveLocations()) {
            handleQuantityTransfer(sendInventory, locationInfo, transferRequestDTO);
        }
    }

    private void handleQuantityTransfer(Inventory sendInventory, ReceiveLocationInfo locationInfo, TransferRequestDTO transferRequestDTO) {
        WarehouseLocation receiveLocation = warehouseLocationRepository.findById(locationInfo.getReceiveWarehouseLocationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 받는 위치가 존재하지 않습니다."));

        if (locationInfo.getQuantity() > sendInventory.getQuantity()) {
            throw new IllegalArgumentException("이동하려는 수량이 현재 재고 수량보다 많습니다.");
        }

        sendInventory.updateQuantity(sendInventory.getQuantity() - locationInfo.getQuantity());
        inventoryRepository.save(sendInventory);

        Inventory newInventory = Inventory.builder()
                .warehouse(sendInventory.getWarehouse())
                .product(sendInventory.getProduct())
                .warehouseLocation(receiveLocation)
                .inventoryNumber(locationInfo.getReceiveInventoryNumber())
                .standard(sendInventory.getStandard())
                .quantity(locationInfo.getQuantity())
                .build();
        inventoryRepository.save(newInventory);

        InventoryHistory inventoryHistory = createInventoryHistory(sendInventory, newInventory, locationInfo, transferRequestDTO);
        inventoryHistoryRepository.save(inventoryHistory);
    }

    private InventoryHistory createInventoryHistory(Inventory sendInventory, Inventory newInventory, ReceiveLocationInfo locationInfo, TransferRequestDTO transferRequestDTO) {
        return InventoryHistory.builder()
                .inventory(sendInventory)
                .receiveInventory(newInventory)
                .warehouse(sendInventory.getWarehouse())
                .sendWarehouseLocation(sendInventory.getWarehouseLocation())
                .receiveWarehouseLocation(newInventory.getWarehouseLocation())
                .employee(employeeRepository.findById(transferRequestDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 작업자를 찾을 수 없습니다.")))
                .workDate(transferRequestDTO.getTransferDate())
                .slipDate(LocalDate.now())
                .workNumber(generateWorkNumber(transferRequestDTO.getTransferDate()))
                .slipNumber(generateSlipNumber(transferRequestDTO.getTransferDate()))
                .workType("수량이동")
                .receiveInventoryNumber(newInventory.getInventoryNumber())
                .workQuantity(locationInfo.getQuantity())
                .summary("")
                .build();
    }

    private Long generateWorkNumber(LocalDate transferDate) {
        return inventoryHistoryRepository.countByWorkDate(transferDate) + 1;
    }

    private Long generateSlipNumber(LocalDate transferDate) {
        return inventoryHistoryRepository.countBySlipDate(transferDate) + 1;
    }

}
