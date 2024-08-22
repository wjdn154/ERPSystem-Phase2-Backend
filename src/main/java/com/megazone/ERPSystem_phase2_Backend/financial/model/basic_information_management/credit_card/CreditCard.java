package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.bank_account.BankAccount;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.enums.CardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 신용카드 정보
@Entity(name = "credit_card")
@Table(name = "credit_card")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 신용카드 테이블의 기본 키

    @Column(nullable = false, unique = true)
    private String cardNumber; // 카드번호

    @Column(nullable = false)
    private TransactionType transactionType; // 유형 (매출, 매입)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType; // 카드 종류 (일반카드, 복지카드, 사업용카드)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_account_id", nullable = false)
    private BankAccount paymentAccount; // 결제 계좌 (BankAccount 테이블 참조)

    private String remarks; // 비고

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

    private BigDecimal fee; // 수수료

    private LocalDate paymentDate; // 결제일

    private BigDecimal limitAmount; // 사용 한도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_company_id", nullable = false)
    private Company company; // 카드사 정보

    @OneToOne(mappedBy = "creditCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ownership ownership; // 신용카드 소유 및 담당자 정보
}