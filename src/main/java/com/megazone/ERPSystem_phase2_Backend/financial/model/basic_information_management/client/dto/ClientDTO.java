package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.AddressDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.EmployeeDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.EmploymentType;
import lombok.*;

import java.time.LocalDate;

/**
 * 거래처 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private AddressDTO address; // 주소 정보
    private BusinessInfoDTO businessInfo; // 업태 및 종목
    private ContactInfoDTO contactInfo; // 연락처 정보
    private FinancialInfoDTO financialInfo; // 재무 정보
    private ManagerInfoDTO managerInfo; // 업체 담당자 정보
    private LiquorDTO liquor; // 주류 정보
    private BankAccountDTO bankAccount; // 은행 계좌 정보
    private CategoryDTO category; // 거래처 분류 정보

    private EmployeeDTO employee; // 담당자 정보
    private Long id; // 거래처 ID
    private String code; // 거래처 코드e

    private TransactionType transactionType; // 거래 유형
    private String businessRegistrationNumber; // 사업자등록번호
    private String idNumber; // 주민등록번호
    private String representativeName; // 대표자명
    private String printClientName; // 거래처명
    private LocalDate transactionStartDate; // 거래 시작일
    private LocalDate transactionEndDate; // 거래 종료일
    private String remarks; // 비고
    private Boolean isActive; // 사용 여부

}