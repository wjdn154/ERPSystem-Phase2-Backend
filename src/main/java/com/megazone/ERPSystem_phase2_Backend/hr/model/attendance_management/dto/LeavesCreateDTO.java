package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesCreateDTO {
    private Long employeeId; // 사원 ID
    private Long leavesTypeId; // 휴가 유형 ID
    private String code; // 휴가 코드
    private LocalDate startDate; // 휴가 시작일
    private LocalDate endDate; // 휴가 종료일
    private String reason; // 휴가 사유
    private LeaveStatus status; // 휴가 상태

    public static LeavesCreateDTO create(Leaves leaves) {
        return new LeavesCreateDTO(
                leaves.getEmployee().getId(),
                leaves.getLeavesType().getId(),
                leaves.getCode(),
                leaves.getStartDate(),
                leaves.getEndDate(),
                leaves.getReason(),
                leaves.getStatus()
        );
    }
}
