package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import lombok.*;

/**
 * 담당자 정보 DTO
 */
@Data
public class EmployeeDTO {
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
}