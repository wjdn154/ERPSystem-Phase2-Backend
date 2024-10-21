package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeStatementServiceImpl implements IncomeStatementService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public Object show(IncomeStatementSearchDTO dto) {
        List<IncomeStatementLedgerShowDTO> resultList = resolvedVoucherRepository.incomeStatementShow(dto);
        return null;
    }
}
