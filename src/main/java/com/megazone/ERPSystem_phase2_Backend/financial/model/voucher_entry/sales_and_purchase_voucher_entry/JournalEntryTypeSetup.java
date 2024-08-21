package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "journal_entry_type_setup")
@Table(name = "journal_entry_type_setup")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryTypeSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_subject_id")
    private AccountSubject accountSubject; // 적용할 계정과목 참조

    private String name;
}
