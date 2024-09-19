package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferShowDTO {
    private LocalDate transfer_date;
    private String employeeName;
    private String transferType;
    private String reason;

    public static TransferShowDTO create(Transfer transfer) {
        return new TransferShowDTO(
                transfer.getTransferDate(),
                transfer.getEmployee().getLastName() + transfer.getEmployee().getFirstName(),
                transfer.getTransferType(),
                transfer.getReason()
        );
    }
}
