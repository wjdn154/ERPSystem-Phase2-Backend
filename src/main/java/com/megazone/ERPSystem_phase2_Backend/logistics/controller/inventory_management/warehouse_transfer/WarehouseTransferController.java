package com.megazone.ERPSystem_phase2_Backend.logistics.controller.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer.WarehouseTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/logistics/warehouseTransfer")
@RequiredArgsConstructor
public class WarehouseTransferController {

    private final WarehouseTransferService transferService;

    @PostMapping("/")
    public ResponseEntity<List<WarehouseTransferResponseListDTO>> getTransfers(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        try {
            List<WarehouseTransferResponseListDTO> transfers = transferService.findTransfers(startDate, endDate);
            if (transfers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(transfers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/{id}")
    public ResponseEntity<WarehouseTransferResponseDTO> getTransferDetail(@PathVariable("id") Long transferId) {
        try {
            Optional<WarehouseTransferResponseDTO> transferDetail = transferService.getTransferDetail(transferId);

            return transferDetail.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/create")
    public ResponseEntity<WarehouseTransferResponseDTO> createTransfer(@RequestBody WarehouseTransferRequestDTO requestDTO) {
        try {
            WarehouseTransferResponseDTO responseDTO = transferService.createWarehouseTransfer(requestDTO);

            return (responseDTO != null) ?
                    ResponseEntity.status(HttpStatus.CREATED).body(responseDTO) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WarehouseTransferResponseDTO> updateTransfer(@PathVariable("id") Long transferId, @RequestBody WarehouseTransferRequestDTO updateDTO) {
        try {
            WarehouseTransferResponseDTO updatedTransfer = transferService.updateTransfer(transferId, updateDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransfer(@PathVariable("id") Long transferId) {
        try {
            transferService.deleteTransfer(transferId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
