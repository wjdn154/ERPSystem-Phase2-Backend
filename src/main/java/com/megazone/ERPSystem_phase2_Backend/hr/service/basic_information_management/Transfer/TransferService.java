package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;

import java.util.List;
import java.util.Optional;

public interface TransferService {
    TransferShowDTO createTransfer(TransferCreateDTO dto);

    List<TransferShowDTO> findAllTransfers();

    Optional<TransferShowDTO> findTransferById(Long id);

    TransferShowDTO updateTransfer(Long id, TransferCreateDTO dto);  // 발령 기록 수정
}

