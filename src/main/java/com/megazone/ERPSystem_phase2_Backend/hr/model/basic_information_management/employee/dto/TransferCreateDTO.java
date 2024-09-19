package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferCreateDTO {
    private LocalDate transfer_date;
    private Long employee_id;
    private String employeeNumber;
    private String employeeName;
    private String fromDepartmentName;
    private String toDepartmentName;
    private String transferType;
    private String reason;


    public TransferCreateDTO create(Transfer transfer) {
        return new TransferCreateDTO(
                transfer.getTransferDate(),
                transfer.getEmployee().getId(),
                transfer.getEmployee().getEmployeeNumber(),
                transfer.getEmployee().getLastName() + "" + transfer.getEmployee().getFirstName(),
                transfer.getFromDepartment().getDepartmentName(),
                transfer.getToDepartment().getDepartmentName(),
                transfer.getTransferType(),
                transfer.getReason()
        );
    }
}
