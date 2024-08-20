package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertificateDTO {
    private String firstName;
    private String lastName;
    private String positionName;
    private Date hireDate;
    private Date issueDate;
    private boolean isCurrentlyEmployed;

}
