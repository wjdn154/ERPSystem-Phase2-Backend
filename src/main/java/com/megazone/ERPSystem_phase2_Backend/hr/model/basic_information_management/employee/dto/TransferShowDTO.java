package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Transfer;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferShowDTO {
    private Long id;
    private LocalDate transferDate;
    private Long employeeId;
    private String employeeNumber;
    private String employeeName; // 사원이름 (성 + 이름)
    private Long fromDepartmentId; // 출발 부서 ID
    private Long toDepartmentId; // 도착 부서 ID
    private String toDepartmentCode;
    private String fromDepartmentCode;
    private String fromDepartmentName;
    private String toDepartmentName;
    private Long transferTypeId;
    private String transferTypeCode;
    private String transferTypeDescription;
    private String reason;



//    public static TransferShowDTO create(Transfer transfer) {
//        TransferShowDTO dto = new TransferShowDTO();
//        dto.setId(transfer.getId());
//        dto.setTransferDate(transfer.getTransferDate());
//        dto.setEmployeeId(transfer.getEmployee().getId());
//        dto.setEmployeeNumber(transfer.getEmployee().getEmployeeNumber()); // 사원번호 설정
//        dto.setEmployeeName(transfer.getEmployee().getLastName() + " " + transfer.getEmployee().getFirstName()); // 사원이름 설정
//        dto.setFromDepartmentId(transfer.getFromDepartment().getId());
//        dto.setToDepartmentId(transfer.getToDepartment().getId());
//        dto.setFromDepartmentCode(transfer.getFromDepartment().getDepartmentCode());
//        dto.setToDepartmentCode(transfer.getToDepartment().getDepartmentCode());
//        dto.setFromDepartmentName(transfer.getFromDepartment().getDepartmentName());
//        dto.setToDepartmentName(transfer.getToDepartment().getDepartmentName());
//        dto.setTransferTypeId(transfer.getTransferType().getId());
//        dto.setTransferTypeCode(transfer.getTransferType().getCode());
//        dto.setTransferTypeDescription(transfer.getTransferType().getDescription());
//        dto.setReason(transfer.getReason());
//        return dto;
//    }
    public static TransferShowDTO create(Transfer transfer) {
        return new TransferShowDTO(
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
                transfer.getTransferType().getId(),
                transfer.getTransferType().getCode(),
                transfer.getTransferType().getDescription(),
                transfer.getReason()
        );
    }
}
