package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.dto.BankAccountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Address;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDTO {
    private String cardNumber; // 카드번호
    private TransactionType transactionType; // 유형 (매출, 매입)
    private String cardType; // 카드 종류 (일반카드, 복지카드, 사업용카드)
    private Long paymentAccountId; // 결제 계좌 ID (BankAccount의 ID)
    private String remarks; // 비고
    private Boolean isActive; // 사용 여부
    private BigDecimal fee; // 수수료
    private LocalDate paymentDate; // 결제일
    private BigDecimal limitAmount; // 사용 한도
    private CompanyDTO company; // 카드사 정보 (CompanyDTO)
    private OwnershipDTO ownership; // 신용카드 소유 및 담당자 정보 (OwnershipDTO)

    public CreditCardDTO(CreditCard creditCard) {
        this.cardNumber = creditCard.getCardNumber();
        this.transactionType = creditCard.getTransactionType();
        this.cardType = creditCard.getCardType().name();
        this.paymentAccountId = creditCard.getPaymentAccount().getId(); // 결제 계좌 ID만 저장

        // 카드사 정보를 CompanyDTO로 변환
        this.company = new CompanyDTO(
                creditCard.getCompany().getId(),
                creditCard.getCompany().getName(),
                creditCard.getCompany().getNumber(),
                creditCard.getCompany().getWebsite()
        );

        // 신용카드 소유 및 담당자 정보를 OwnershipDTO로 변환
//        if (creditCard.getOwnership() != null) {
//            this.ownership = new OwnershipDTO(
//                    creditCard.getOwnership().getCreditCard().getId(),
//                    creditCard.getOwnership().getOwner() != null ? creditCard.getOwnership().getOwner().getId() : null,
//                    creditCard.getOwnership().getManager() != null ? creditCard.getOwnership().getManager().getId() : null
//            );
//        }

        this.remarks = creditCard.getRemarks();
        this.isActive = creditCard.getIsActive();
        this.fee = creditCard.getFee();
        this.paymentDate = creditCard.getPaymentDate();
        this.limitAmount = creditCard.getLimitAmount();
    }
}