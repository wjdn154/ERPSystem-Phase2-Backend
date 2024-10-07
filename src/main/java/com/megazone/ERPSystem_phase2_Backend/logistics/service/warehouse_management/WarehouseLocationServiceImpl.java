package com.megazone.ERPSystem_phase2_Backend.logistics.service.warehouse_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.dto.WarehouseLocationResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_location_management.WarehouseLocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseLocationServiceImpl implements WarehouseLocationService {

    private final WarehouseLocationRepository warehouseLocationRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public List<WarehouseLocationResponseDTO> getLocationsByWarehouseId(Long warehouseId) {
        List<WarehouseLocation> locations = warehouseLocationRepository.findByWarehouseId(warehouseId);

        return locations.stream()
                .map(WarehouseLocationResponseDTO::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseLocationResponseDTO createWarehouseLocation(WarehouseLocationRequestDTO requestDTO) {
        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 창고가 존재하지 않습니다."));

        warehouseLocationRepository.findByWarehouseIdAndLocationName(requestDTO.getWarehouseId(), requestDTO.getLocationName())
                .ifPresent(existingLocation -> {
                    throw new IllegalArgumentException("해당 창고에 이미 동일한 위치 이름이 존재합니다.");
                });

        WarehouseLocation warehouseLocation = requestDTO.mapToEntity(warehouse);

        WarehouseLocation savedLocation = warehouseLocationRepository.save(warehouseLocation);

        return WarehouseLocationResponseDTO.mapToDTO(savedLocation);
    }

    @Override
    public WarehouseLocationResponseDTO updateWarehouseLocation(Long id, WarehouseLocationRequestDTO requestDTO) {
        WarehouseLocation location = warehouseLocationRepository.findByIdAndWarehouseId(id, requestDTO.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("해당 ID와 Warehouse ID에 대한 창고 위치가 존재하지 않습니다."));

        if (warehouseLocationRepository.existsByWarehouseIdAndLocationName(requestDTO.getWarehouseId(), requestDTO.getLocationName())) {
            throw new RuntimeException("이미 존재하는 위치 이름입니다. 다른 이름을 입력해 주세요.");
        }

        WarehouseLocation updatedLocation = WarehouseLocation.builder()
                .id(location.getId())
                .warehouse(location.getWarehouse())
                .locationName(requestDTO.getLocationName())
                .isActive(requestDTO.isActive())
                .build();

        WarehouseLocation savedLocation = warehouseLocationRepository.save(updatedLocation);

        return WarehouseLocationResponseDTO.mapToDTO(savedLocation);
    }

    @Override
    public void deleteWarehouseLocation(Long id) {
        WarehouseLocation location = warehouseLocationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 대한 창고 위치가 존재하지 않습니다."));

        boolean inventoryExists = inventoryRepository.existsByWarehouseLocationId(location.getId());
        if (inventoryExists) {
            throw new IllegalStateException("해당 위치에 재고가 존재하여 삭제할 수 없습니다.");
        }

        warehouseLocationRepository.delete(location);
    }
}
