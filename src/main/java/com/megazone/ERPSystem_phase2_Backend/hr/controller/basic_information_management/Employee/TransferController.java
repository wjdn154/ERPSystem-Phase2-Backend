package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.DepartmentDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer.TransferService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class TransferController {

    private final TransferService transferService;

    // 발령 기록 생성
    @PostMapping("/transfer/create")
    public ResponseEntity<TransferShowDTO> createTransfer(@RequestBody TransferCreateDTO dto) {
        Optional<TransferShowDTO> savedTransfer =transferService.createTransfer(dto);
        return savedTransfer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());


    }

    // 발령 기록 리스트 조회
    @PostMapping("/transfer/all")
    public ResponseEntity<List<TransferShowDTO>> findAllTransfers() {
        List<TransferShowDTO> transfers = transferService.findAllTransfers();
        return ResponseEntity.ok(transfers);
    }

    // 발령 기록 상세 조회
    @PostMapping("/transfer/detail/{id}")
    public ResponseEntity<TransferShowDTO> findTransferById(@PathVariable("id") Long id) {
        Optional<TransferShowDTO> transfer = transferService.findTransferById(id);
        return transfer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    // 발령 기록 수정
    @PostMapping("/transfer/update/{id}")
    public ResponseEntity<TransferShowDTO> updateTransfer(
            @PathVariable Long id,
            @RequestBody TransferUpdateDTO dto) {

        try {
            TransferShowDTO transferShowDTO = transferService.updateTransfer(id, dto);
            return ResponseEntity.ok(transferShowDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}