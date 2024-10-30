package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.TransferType;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferTypeUpdateDTO;

public interface TransferTypeService {
    TransferType registerTransferType(TransferTypeCreateDTO transferTypeCreateDTO);

    TransferType updateTransferType(Long id, TransferTypeUpdateDTO transferTypeUpdateDTO);
}
