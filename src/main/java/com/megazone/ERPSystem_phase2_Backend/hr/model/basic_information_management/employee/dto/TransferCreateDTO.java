package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
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
    private Employee employeeNumber;
    private Employee employeeName;
    private Department fromDepartmentName;
    private Department toDepartmentName;
    private String transferType;
    private String reason;

    public TransferCreateDTO(LocalDate transferDate, String employeeNumber, String s, String departmentName, String departmentName1, String transferType, String reason) {
    }

    public TransferCreateDTO create(Transfer transfer) {
        return new TransferCreateDTO(
                transfer.getTransferDate(),
                transfer.getEmployee().getEmployeeNumber(),
                transfer.getEmployee().getLastName() + "" + transfer.getEmployee().getFirstName(),
                transfer.getFromDepartment().getDepartmentName(),
                transfer.getToDepartment().getDepartmentName(),
                transfer.getTransferType(),
                transfer.getReason()
        );
    }
}
