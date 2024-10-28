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
    private LocalDate transferDate; // 발령 날짜
    private Long employeeId; // 사원 ID
    private String employeeNumber; // 사원 번호
    private String employeeName; // 사원이름 (성 + 이름)
    private Long fromDepartmentId; // 출발 부서 ID
    private Long toDepartmentId; // 도착 부서 ID
    private String todepartmentCode;
    private String fromdepartmentCode;
    private String fromDepartmentName; // 출발 부서 이름
    private String toDepartmentName; // 도착 부서 이름
    private Long transferTypeId;
    private String transferTypeCode; // 발령 유형 코드
    private String transferTypeDescription; // 발령 유형 설명
    private String reason; // 발령 사유


    public TransferCreateDTO create(Transfer transfer) {
        return new TransferCreateDTO(
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
                transfer.getTransferType().getId(),
                transfer.getTransferType().getCode(),
                transfer.getTransferType().getDescription(),
                transfer.getReason()
        );};
}
