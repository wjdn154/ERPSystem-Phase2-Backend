package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.TransferType.TransferTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class TransferTypeController {
    private final TransferTypeService transferTypeService;

    // TransferType 등록 API
    @PostMapping("/transferType/register")
    public ResponseEntity<TransferTypeResponseDTO> registerTransferType(@RequestBody TransferTypeCreateDTO transferTypeCreateDTO) {
        TransferType createdTransferType = transferTypeService.registerTransferType(transferTypeCreateDTO);
        TransferTypeResponseDTO responseDTO = new TransferTypeResponseDTO(
                createdTransferType.getId(),
                createdTransferType.getCode(),
                createdTransferType.getDescription()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // TransferType 수정 API
    @PostMapping("/transferType/update/{id}")
    public ResponseEntity<TransferTypeResponseDTO> updateTransferType(
            @PathVariable Long id,
            @RequestBody TransferTypeUpdateDTO transferTypeUpdateDTO) {

        // 서비스에서 TransferType 업데이트 로직 호출
        TransferType updatedTransferType = transferTypeService.updateTransferType(id, transferTypeUpdateDTO);

        // 응답 DTO 생성
        TransferTypeResponseDTO responseDTO = new TransferTypeResponseDTO(
                updatedTransferType.getId(),
                updatedTransferType.getCode(),
                updatedTransferType.getDescription()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
