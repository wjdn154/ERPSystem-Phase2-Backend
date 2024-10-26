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
    private String registrationNumber; // 생년월일
    private String phoneNumber;    // 전화번호
    private EmploymentStatus employmentStatus;
    private EmploymentType employmentType;
    private String email;          // 이메일
    private String address;        // 주소
    private LocalDate hireDate;    // 채용일
    private boolean householdHead; // 가구주 여부
    private String imagePath;
    private String departmentCode;
    private String positionName;
    private String titleName;
    private Long departmentId;
    private Long positionId;
    private Long jobTitleId;
    //private Long bankAccountId;    // 은행 계좌 ID
    private BankAccountDTO bankAccountDTO;

    public static EmployeeCreateDTO create(EmployeeCreateParseDTO dto) {
        return new EmployeeCreateDTO(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getRegistrationNumber(),
                dto.getPhoneNumber(),
                dto.getEmploymentStatus(),
                dto.getEmploymentType(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getHireDate() != null ? LocalDate.parse(dto.getHireDate()) : null, // null 값 체크
                dto.isHouseholdHead(),
                dto.getProfilePicture(),
                dto.getDepartmentCode(),
                dto.getPositionName(),
                dto.getTitleName(),
                dto.getDepartmentId(),
                dto.getPositionId(),
                dto.getTitleId(),
                BankAccountDTO.create(
                        dto.getDepartmentId(),
                        dto.getFirstName(),
                        dto.getAddress(),
                        dto.getPhoneNumber()
                )
        );
    }
}
