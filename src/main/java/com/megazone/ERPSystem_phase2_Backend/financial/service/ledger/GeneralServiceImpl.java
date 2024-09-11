package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.ResolvedVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralServiceImpl implements GeneralService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<GeneralShowDTO> getGeneralShow(GeneralDTO dto) {
        List<GeneralShowDTO> resolvedVouchers = resolvedVoucherRepository.generalSearch(
                dto.getStartDate(),dto.getEndDate(),
                dto.getStartSubjectCode(),dto.getEndSubjectCode());
        return resolvedVouchers;
    }


}
