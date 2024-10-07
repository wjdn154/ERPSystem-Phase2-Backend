package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.dto.InventoryResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_location_management.WarehouseLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final WarehouseLocationRepository locationRepository;


    @Override
    public List<InventoryResponseDTO> getAllInventories() {
        return inventoryRepository.findAllInventories();
    }

    @Override
    public List<InventoryResponseDTO> getInventoriesByLocationId(Long locationId) {
        return inventoryRepository.findInventoriesByLocationId(locationId);
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO requestDTO) {
        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("해당 창고 ID가 존재하지 않습니다."));

        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 품목 ID가 존재하지 않습니다."));

        WarehouseLocation location = locationRepository.findById(requestDTO.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 위치 ID가 존재하지 않습니다."));

        if (inventoryRepository.existsByInventoryNumber(requestDTO.getInventoryNumber())) {
            throw new IllegalArgumentException("중복된 재고 번호가 존재합니다.");
        }

        Inventory inventory = Inventory.builder()
                .warehouse(warehouse)
                .product(product)
                .warehouseLocation(location)
                .inventoryNumber(requestDTO.getInventoryNumber())
                .standard(requestDTO.getStandard())
                .quantity(requestDTO.getQuantity())
                .build();

        Inventory savedInventory = inventoryRepository.save(inventory);

        return InventoryResponseDTO.mapToDTO(savedInventory);
    }
}
