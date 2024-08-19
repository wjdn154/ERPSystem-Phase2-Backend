package com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry.enums;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
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
    @JoinColumn(name = "account_subject_id")
    private AccountSubject accountSubject;

    private String code;

    private String name;

    private TransactionType transactionType;
}
