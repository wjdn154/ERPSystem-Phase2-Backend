package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.LeavesType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesCreateDTO {
    private Long id;
    private LeavesType leavesType;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus leaveStatus;

    public static LeavesCreateDTO create(LeavesCreateDTO leavesCreateDTO) {
        return new LeavesCreateDTO(
                leavesCreateDTO.getId(),
                leavesCreateDTO.getLeavesType(),
                leavesCreateDTO.getCode(),
                leavesCreateDTO.getStartDate(),
                leavesCreateDTO.getEndDate(),
                leavesCreateDTO.getReason(),
                leavesCreateDTO.getLeaveStatus()
        );
    }
}
