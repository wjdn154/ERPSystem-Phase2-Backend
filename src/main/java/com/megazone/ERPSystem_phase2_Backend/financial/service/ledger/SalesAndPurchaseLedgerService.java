package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowAllDTO;

import java.util.List;

public interface SalesAndPurchaseLedgerService {
    List<SalesAndPurChaseLedgerShowAllDTO> showAll(SalesAndPurChaseLedgerSearchDTO dto);
}
