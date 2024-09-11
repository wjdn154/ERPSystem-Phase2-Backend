package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.GeneralShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GeneralServiceImpl implements GeneralService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<GeneralShowDTO> getGeneralShow(GeneralDTO dto, Long companyId) {
        List<GeneralShowDTO> resolvedVouchers = resolvedVoucherRepository.generalSearch(
                dto.getStartDate(),dto.getEndDate(),
                dto.getStartSubjectCode(),dto.getEndSubjectCode(),companyId);
        return resolvedVouchers;
    }


}
