package com.megazone.ERPSystem_phase2_Backend.logistics.controller.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer.WarehouseTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logistics/warehouseTransfer")
@RequiredArgsConstructor
public class WarehouseTransferController {

    private final WarehouseTransferService warehouseTransferService;
    private final UsersRepository usersRepository;

    @PostMapping("/")
    public ResponseEntity<List<WarehouseTransferResponseDTO>> getWarehouseTransfers() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        List<WarehouseTransferResponseDTO> transfers = warehouseTransferService.getWarehouseTransfers(companyId);
        return ResponseEntity.ok(transfers);
    }
}
