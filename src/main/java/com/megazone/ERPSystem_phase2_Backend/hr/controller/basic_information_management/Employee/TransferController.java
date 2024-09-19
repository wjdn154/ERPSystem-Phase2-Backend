package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class TransferController {

    private TransferService transferService;

    @PostMapping("/transfer/create")
    public ResponseEntity<TransferCreateDTO> createTransfer(@RequestBody TransferCreateDTO dto) {
        Optional<TransferCreateDTO> savedTransfer = transferService.createTransfer(dto);
        return savedTransfer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
