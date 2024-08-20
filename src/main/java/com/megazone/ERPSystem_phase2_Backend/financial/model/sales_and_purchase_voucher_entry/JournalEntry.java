package com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "journal_entry")
@Table(name="journal_entry")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_subject_code",referencedColumnName = "code")
    private AccountSubject accountSubject; // 적용할 계정과목 참조

    @Column(unique = true, nullable = false)
    private String code; // 분개유형 코드

    private String name; // 분개유형 이름

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 적용타입이 매출, 매입 인지 확인
}
