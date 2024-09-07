package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JournalEntryTypeSetupShowDTO {
    private Long journalEntryTypeId;
    private String journalEntryTypeName;
    private String accountSubjectName;
    private String accountSubjectCode;

    public static JournalEntryTypeSetupShowDTO create(JournalEntryTypeSetup journalEntryTypeSetup) {
        return new JournalEntryTypeSetupShowDTO(
                journalEntryTypeSetup.getId(),
                journalEntryTypeSetup.getName(),
                journalEntryTypeSetup.getAccountSubject().getName(),
                journalEntryTypeSetup.getAccountSubject().getCode()
        );
    }
}
