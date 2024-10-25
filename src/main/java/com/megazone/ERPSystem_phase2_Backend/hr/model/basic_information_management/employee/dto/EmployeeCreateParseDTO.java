package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateParseDTO {
    private String firstName;      // 이름
    private String lastName;       // 성
    private String registrationNumber; // 생년월일
    private String phoneNumber;    // 전화번호
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;          // 이메일
    private String address;        // 주소
    private String hireDate;    // 채용일
    private boolean householdHead; // 가구주 여부
    private String imagePath;
    private Long departmentId;
    private String departmentCode;
    private String positionName;
    private String titleName;
    private Long positionId;
    private Long jobTitleId;
    //private Long bankAccountId;    // 은행 계좌 ID
    private Long bankId;
    private String name;
    private String accountNumber;
    private String code;
}
