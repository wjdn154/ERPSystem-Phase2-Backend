package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.TransferType;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeCreateDTO;
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
}
