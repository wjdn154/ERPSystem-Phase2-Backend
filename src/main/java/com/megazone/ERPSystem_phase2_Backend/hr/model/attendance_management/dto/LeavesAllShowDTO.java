package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesAllShowDTO {
    private Long id;
    private String typeName;
    private String startDate;
    private String endDate;
    private LeaveStatus status;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String positionName;

    // 엔티티에서 DTO로 변환하는 메서드
    public static LeavesAllShowDTO fromEntity(Leaves leave) {
        LeavesAllShowDTO dto = new LeavesAllShowDTO();
        dto.setId(leave.getId());
        dto.setTypeName(leave.getLeavesType().getTypeName());
        dto.setStartDate(leave.getStartDate().toString());
        dto.setEndDate(leave.getEndDate().toString());
        dto.setStatus(leave.getStatus());
        dto.setEmployeeId(leave.getEmployee().getId());
        dto.setFirstName(leave.getEmployee().getFirstName());
        dto.setLastName(leave.getEmployee().getLastName());
        dto.setEmployeeNumber(leave.getEmployee().getEmployeeNumber());
        dto.setPositionName(leave.getEmployee().getPosition().getPositionName());
        return dto;
    }
}
