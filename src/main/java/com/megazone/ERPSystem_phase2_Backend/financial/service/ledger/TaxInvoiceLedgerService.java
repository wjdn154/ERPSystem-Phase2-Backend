package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;

public interface TaxInvoiceLedgerService {
    Object show(TaxInvoiceLedgerSearchDTO dto);
}
