package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerAccListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerDetailEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerShowAllListDTO;

import java.util.List;

public interface AccountLedgerService {
    List<AccountLedgerAccListDTO> getAccountLedgerAccList(AccountLedgerSearchDTO dto);

    AccountLedgerShowAllListDTO getAccountLedgerShow(AccountLedgerDetailEntryDTO dto);
}
