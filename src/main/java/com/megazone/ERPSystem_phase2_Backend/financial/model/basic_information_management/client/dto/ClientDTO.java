package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.AddressDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
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

    private String code; // 거래처 코드
    private String departmentEmployee; // 담당 부서 사원 코드
    private String transactionType; // 거래 유형
    private String businessRegistrationNumber; // 사업자등록번호
    private String idNumber; // 주민등록번호
    private String representativeName; // 대표자명
    private String printClientName; // 거래처명
    private LocalDate transactionStartDate; // 거래 시작일
    private LocalDate transactionEndDate; // 거래 종료일
    private String remarks; // 비고
    private Boolean isActive; // 사용 여부

    /**
     * Client 엔티티를 받아 DTO로 변환하는 생성자임
     *
     * @param client 변환할 Client 엔티티 객체
     */
    public ClientDTO(Client client) {
        this.address = new AddressDTO(
                client.getAddress().getId(),
                client.getAddress().getPostalCode(),
                client.getAddress().getRoadAddress(),
                client.getAddress().getDetailedAddress()
        );
        this.businessInfo = new BusinessInfoDTO(
                client.getBusinessInfo().getBusinessType(),
                client.getBusinessInfo().getBusinessItem()
        );
        this.contactInfo = new ContactInfoDTO(
                client.getContactInfo().getPhoneNumber(),
                client.getContactInfo().getFaxNumber()
        );
        this.financialInfo = new FinancialInfoDTO(
                client.getFinancialInfo().getCollateralAmount(),
                client.getFinancialInfo().getCreditLimit()
        );
        this.managerInfo = new ManagerInfoDTO(
                client.getManagerInfo().getClientManagerPhoneNumber(),
                client.getManagerInfo().getClientManagerEmail()
        );
        this.liquor = new LiquorDTO(
                client.getLiquor().getId(),
                client.getLiquor().getCode(),
                client.getLiquor().getName()
        );
        this.bankAccount = new BankAccountDTO(
                new BankDTO(
                        client.getBankAccount().getBank().getId(),
                        client.getBankAccount().getBank().getCode(),
                        client.getBankAccount().getBank().getName(),
                        client.getBankAccount().getBank().getBusinessNumber()
                ),
                client.getBankAccount().getAccountNumber(),
                client.getBankAccount().getAccountHolder()
        );
        this.category = new CategoryDTO(
                client.getCategory().getId(),
                client.getCategory().getCode(),
                client.getCategory().getName()
        );
        this.departmentEmployee = client.getDepartmentEmployee();
        this.transactionType = client.getTransactionType().name();
        this.businessRegistrationNumber = client.getBusinessRegistrationNumber();
        this.idNumber = client.getIdNumber();
        this.representativeName = client.getRepresentativeName();
        this.printClientName = client.getPrintClientName();
        this.transactionStartDate = client.getTransactionStartDate();
        this.transactionEndDate = client.getTransactionEndDate();
        this.remarks = client.getRemarks();
        this.isActive = client.getIsActive();
    }
}