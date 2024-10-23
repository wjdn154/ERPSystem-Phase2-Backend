package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
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


    private Long departmentId; // 부서 ID
    private Long positionId; // 직위 ID
    private Long jobTitleId; // 직책 ID
    private BankAccountDTO bankAccountDTO; // 계좌 번호



    public static EmployeeDTO create(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeNumber(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDateOfBirth(),
                employee.getPhoneNumber(),
                employee.getEmploymentStatus(),
                employee.getEmploymentType(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getHireDate(),
                employee.isHouseholdHead(),
                employee.getImagePath(),
                employee.getDepartment().getId(),
                employee.getPosition().getId(),
                employee.getJobTitle().getId(),
                BankAccountDTO.create(employee.getBankAccount())
        );
    }

}
