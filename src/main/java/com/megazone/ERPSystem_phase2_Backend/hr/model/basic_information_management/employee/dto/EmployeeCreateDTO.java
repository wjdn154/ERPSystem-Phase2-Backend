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
public class EmployeeCreateDTO {
    private String firstName;      // 이름
    private String lastName;       // 성
    private LocalDate dateOfBirth; // 생년월일
    private String phoneNumber;    // 전화번호
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;          // 이메일
    private String address;        // 주소
    private LocalDate hireDate;    // 채용일
    private boolean householdHead; // 가구주 여부
    private String profilePicture; // 프로필 사진 URL
    private Long departmentId;     // 부서 ID
    private Long positionId;       // 직책 ID
    private Long jobTitleId;       // 직무명 ID
    //private Long bankAccountId;    // 은행 계좌 ID
    private BankAccountDTO bankAccountDTO;
}
