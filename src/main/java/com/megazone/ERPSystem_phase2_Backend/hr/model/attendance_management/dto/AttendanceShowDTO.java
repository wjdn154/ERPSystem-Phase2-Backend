package com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceShowDTO {
    private String attendanceCode;
    private EmployeeAttendanceDTO employeeAttendanceDTO;
}
