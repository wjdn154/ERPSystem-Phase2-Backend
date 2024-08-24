package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JournalEntryShowDTO {
    private String journalEntryCode;
    private String journalEntryName;

    public static JournalEntryShowDTO create(String journalEntryCode, String journalEntryName) {
        return new JournalEntryShowDTO(
                journalEntryCode,
                journalEntryName
        );
    }
}
