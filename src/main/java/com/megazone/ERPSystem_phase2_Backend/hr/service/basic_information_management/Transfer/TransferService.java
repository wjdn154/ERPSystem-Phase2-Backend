package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;

import java.util.Optional;

public interface TransferService {
    Optional<TransferCreateDTO> createTransfer(TransferCreateDTO dto);
}
