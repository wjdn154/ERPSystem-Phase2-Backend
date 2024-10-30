package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.TransferType;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.TransferType.TransferTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferTypeServiceImpl implements TransferTypeService {

    private final TransferTypeRepository transferTypeRepository;

    public TransferType registerTransferType(TransferTypeCreateDTO transferTypeCreateDTO) {
        TransferType transferType = new TransferType();
        transferType.setCode(transferTypeCreateDTO.getCode());
        transferType.setDescription(transferTypeCreateDTO.getDescription());
        return transferTypeRepository.save(transferType);
    }

    public TransferType updateTransferType(Long id, TransferTypeUpdateDTO transferTypeUpdateDTO) {
        TransferType transferType = transferTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransferType not found with id: " + id));

        if (transferTypeUpdateDTO.getCode() != null) {
            transferType.setCode(transferTypeUpdateDTO.getCode());
        }
        if (transferTypeUpdateDTO.getDescription() != null) {
            transferType.setDescription(transferTypeUpdateDTO.getDescription());
        }

        return transferTypeRepository.save(transferType);
    }
}