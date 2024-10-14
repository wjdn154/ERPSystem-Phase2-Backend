package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.VoucherPrintSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;

import java.util.List;

public interface VoucherPrintLedgerService {
    List<ResolvedVoucherShowDTO> VoucherPrintList(VoucherPrintSearchDTO dto);
}
