package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingDTO {
    private Long id;
    private String departmentName;
    private String title;
    private String description;
    private LocalDate postingDate;
    private LocalDate closingDate;
}
