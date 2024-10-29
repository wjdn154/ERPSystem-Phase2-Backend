package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 사원 모두 보이는 리스트

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeShowDTO {
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;
    private LocalDate hireDate;

    private String imagePath;
    private String departmentCode;
    private String departmentName; // 부서 이름
    private Long positionId; // 직위 id
    private String positionName; // 직위 이름
    private String titleName; // 직책 이름
//    private String bankAccountNumber; // 계좌 번호


}
