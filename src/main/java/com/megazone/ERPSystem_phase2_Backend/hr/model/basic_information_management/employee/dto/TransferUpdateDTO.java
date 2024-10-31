package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferUpdateDTO {
    private Long id;
    private LocalDate transferDate; // 발령 날짜
    private Long employeeId; // 사원 ID
    private String employeeNumber; // 사원 번호
    private String employeeName; // 사원이름 (성 + 이름)

    private Long fromDepartment;
    private Long toDepartment;
    private String toDepartmentCode;
    private String fromDepartmentCode;
    private String fromDepartmentName; // 출발 부서 이름
    private String toDepartmentName; // 도착 부서 이름
    private String reason; // 발령 사유
    private TransferType transferType;


    public TransferUpdateDTO update(Transfer transfer) {
        return new TransferUpdateDTO(
                transfer.getId(),
                transfer.getTransferDate(),
                transfer.getEmployee().getId(),
                transfer.getEmployee().getEmployeeNumber(),
                transfer.getEmployee().getLastName() + transfer.getEmployee().getFirstName(),
                transfer.getFromDepartment().getId(),
                transfer.getToDepartment().getId(),
                transfer.getFromDepartment().getDepartmentCode(),
                transfer.getToDepartment().getDepartmentCode(),
                transfer.getFromDepartment().getDepartmentName(),
                transfer.getToDepartment().getDepartmentName(),
                transfer.getReason(),
                transfer.getTransferType()
        );}
}
