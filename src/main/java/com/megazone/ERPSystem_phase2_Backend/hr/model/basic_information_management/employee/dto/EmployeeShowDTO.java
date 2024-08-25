package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.BankAccount;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.JobTitle;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
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

    public EmployeeShowDTO(Long id, String employeeNumber, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, EmploymentStatus employmentStatus, EmploymentType employmentType, String email, String address, LocalDate hireDate, boolean householdHead, String profilePicture, Long aLong, Long aLong1, Long aLong2, Long aLong3) {
    }
}
