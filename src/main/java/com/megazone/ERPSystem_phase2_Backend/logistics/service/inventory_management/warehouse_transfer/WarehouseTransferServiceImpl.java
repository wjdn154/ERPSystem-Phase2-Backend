package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer.WarehouseTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseTransferServiceImpl implements WarehouseTransferService {

    private final WarehouseTransferRepository warehouseTransferRepository;

    @Override
    public List<WarehouseTransferResponseDTO> getWarehouseTransfers(Long companyId) {
        List<WarehouseTransfer> transfers = warehouseTransferRepository.findAllByCompanyId(companyId);
        return transfers.stream()
                .map(WarehouseTransferResponseDTO::new)
                .collect(Collectors.toList());
    }
}
