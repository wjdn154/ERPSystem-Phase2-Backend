package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

// 사원 상세 조회

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="employee")
public class EmployeeOneDTO {
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;
    private String address;
    private LocalDate hireDate;
    private boolean isHouseholdHead;
    private String profilePicture;

    private String departmentName; // 부서 이름
    private String positionName; // 직위 이름
    private String jobTitleName; // 직책 이름
    private String bankAccountNumber; // 계좌 번호
}
