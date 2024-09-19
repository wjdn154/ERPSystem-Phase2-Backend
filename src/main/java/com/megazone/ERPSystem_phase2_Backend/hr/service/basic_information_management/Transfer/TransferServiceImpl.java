package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.TransferShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Transfer.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;

    @Override
    public TransferShowDTO createTransfer(TransferCreateDTO dto) {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(dto.getTransfer_date());
        transfer.setEmployee(dto.getEmployeeName());
        transfer.setEmployee(dto.getEmployeeName());
        transfer.setFromDepartment(dto.getFromDepartmentName());
        transfer.setToDepartment(dto.getToDepartmentName());
        transfer.getTransferType();
        transfer.getReason();

        Transfer savedTransfer = transferRepository.save(transfer);
        return TransferShowDTO.create(savedTransfer);
    }

}
