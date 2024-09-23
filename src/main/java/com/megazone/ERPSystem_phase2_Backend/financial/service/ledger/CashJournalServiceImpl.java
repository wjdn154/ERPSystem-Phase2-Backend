package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.CashJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CashJournalServiceImpl implements CashJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public CashJournalShowAllDTO showAll(CashJournalSearchDTO dto) {

//        CashJournalShowAllDTO result = resolvedVoucherRepository.cashJournalShow(dto);
        List<CashJournalShowDTO> result = resolvedVoucherRepository.cashJournalShow(dto);
        return null;
    }
}
