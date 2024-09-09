package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.ResolvedVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralServiceImpl implements GeneralService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;
}
