package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer.TransferService;
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
        Optional<TransferShowDTO> savedTransfer = Optional.ofNullable(transferService.createTransfer(dto));
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
}
