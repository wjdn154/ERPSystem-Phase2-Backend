package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="employee")
public class EmployeeFindDTO {
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String registrationNumber;
    private String phoneNumber;
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;
    private String address;
    private LocalDate hireDate;
    private boolean isHouseholdHead;
    private String profilePicture;


    private Long departmentId; // 부서 ID
    private Long positionId; // 직위 ID
    private Long jobTitleId; // 직책 ID
    private Long bankAccountId; // 은행 계좌 ID
    private String code;
    private String bankName;
    private String accountNumber;


}
