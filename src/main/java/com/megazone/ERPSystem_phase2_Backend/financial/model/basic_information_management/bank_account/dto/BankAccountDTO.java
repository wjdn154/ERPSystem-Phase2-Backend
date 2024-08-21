package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.BankAccount;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.ContactDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.AddressDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.dto.BankDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 은행 계좌 정보 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private AccountTypeDTO accountType; // 예금 유형 정보
    private BankDTO bank; // 은행 정보
    private AddressDTO address; // 주소 정보 (선택 사항)
    private ContactDTO contact; // 연락처 정보
    private String clientName; // 거래처명
    private String accountNumber; // 계좌번호
    private String bankBranchName; // 은행 지점명
    private LocalDate accountOpeningDate; // 계좌 개설일
    private String depositType; // 예금종류
    private LocalDate maturityDate; // 예금 만기일
    private BigDecimal interestRate; // 이자율
    private BigDecimal monthlyPayment; // 매월 납입액
    private BigDecimal overdraftLimit; // 당좌한도액
    private Boolean businessAccount; // 사업용 계좌 여부

    public BankAccountDTO(BankAccount bankAccount) {
        this.accountType = new AccountTypeDTO(
                bankAccount.getAccountType().getId(),
                bankAccount.getAccountType().getCode(),
                bankAccount.getAccountType().getTypeName()
        );
        this.bank = new BankDTO(
                bankAccount.getBank().getId(),
                bankAccount.getBank().getName(),
                bankAccount.getBank().getCode(),
                bankAccount.getBank().getBusinessNumber()
        );
        this.address = bankAccount.getAddress() != null ? new AddressDTO(
                bankAccount.getAddress().getId(),
                bankAccount.getAddress().getPostalCode(),
                bankAccount.getAddress().getRoadAddress(),
                bankAccount.getAddress().getDetailedAddress()
        ) : null;
        this.contact = bankAccount.getContact() != null ? new ContactDTO(
                bankAccount.getContact().getPhone(),
                bankAccount.getContact().getFax()
        ) : null;
        this.clientName = bankAccount.getClientName();
        this.accountNumber = bankAccount.getAccountNumber();
        this.bankBranchName = bankAccount.getBankBranchName();
        this.accountOpeningDate = bankAccount.getAccountOpeningDate();
        this.depositType = bankAccount.getDepositType();
        this.maturityDate = bankAccount.getMaturityDate();
        this.interestRate = bankAccount.getInterestRate();
        this.monthlyPayment = bankAccount.getMonthlyPayment();
        this.overdraftLimit = bankAccount.getOverdraftLimit();
        this.businessAccount = bankAccount.getBusinessAccount();
    }
}