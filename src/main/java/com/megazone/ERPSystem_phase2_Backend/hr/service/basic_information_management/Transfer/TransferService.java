package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface TransferService {
    Optional<TransferShowDTO> createTransfer(TransferCreateDTO dto);

    List<TransferShowDTO> findAllTransfers();

    Optional<TransferShowDTO> findTransferById(Long id);

    TransferShowDTO updateTransfer(Long id, TransferUpdateDTO dto);  // 발령 기록 수정
}

