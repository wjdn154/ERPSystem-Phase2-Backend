package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.InventoryInspectionStatusResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection.InventoryInspectionDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryInspectionDetailServiceImpl implements InventoryInspectionDetailService {

    private final InventoryInspectionDetailRepository inventoryInspectionDetailRepository;

    @Override
    public List<InventoryInspectionStatusResponseListDTO> getInspectionStatusByDateRange(LocalDate startDate, LocalDate endDate) {
        return inventoryInspectionDetailRepository.findByInspectionDateBetween(startDate, endDate).stream()
                .map(InventoryInspectionStatusResponseListDTO::mapToDTO)
                .toList();
    }

}
