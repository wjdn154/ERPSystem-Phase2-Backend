package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupUpdateDTO;

import java.util.List;

public interface JournalEntryTypeSetupService {
    String updateEntryTypeSetup(List<JournalEntryTypeSetupUpdateDTO> dto);
}
