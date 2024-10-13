package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.VoucherPrintSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherPrintLedgerServiceImpl implements VoucherPrintLedgerService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<ResolvedVoucherShowDTO> VoucherPrintList(VoucherPrintSearchDTO dto) {
        return resolvedVoucherRepository.voucherPrintShowList(dto);
    }
}
