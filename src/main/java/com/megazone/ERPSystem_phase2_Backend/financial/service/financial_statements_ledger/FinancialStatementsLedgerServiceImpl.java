package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialStatementsLedgerServiceImpl implements FinancialStatementsLedgerService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;


    @Override
    public Object show(FinancialStatementsLedgerSearchDTO dto) {
        List<FinancialStatementsLedgerShowDTO> result = resolvedVoucherRepository.financialStatementsShow(dto);
        System.out.println(result.toString());
        return null;
    }
}
