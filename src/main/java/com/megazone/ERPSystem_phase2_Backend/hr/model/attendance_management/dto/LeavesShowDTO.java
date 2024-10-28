package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.LeavesType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesShowDTO {
    private Long id;
    private String employeeName;
    private String typeName;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;

    public static LeavesShowDTO create(Leaves leaves) {
        return new LeavesShowDTO(
                leaves.getId(),
               leaves.getEmployee().getLastName() + leaves.getEmployee().getFirstName(),
                leaves.getLeavesType().getTypeName(),
                leaves.getCode(),
                leaves.getStartDate(),
                leaves.getEndDate(),
                leaves.getReason(),
                leaves.getStatus()
        );
    }

}
