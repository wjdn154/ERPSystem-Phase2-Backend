package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientLedgerAndAccountSubjectServiceImpl implements ClientLedgerAndAccountSubjectService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<ClientListDTO> show(ClientAndAccountSubjectLedgerSearchDTO dto) {
        List<ClientListDTO> clientListDTOS = resolvedVoucherRepository.clientAndAccountSubjectLedgerList(dto);

        return clientListDTOS;

    }

    @Override
    public ClientAndAccountSubjectLedgerShowDetailAllDTO showDetail(ClientAndAccountSubjectLedgerShowDetailConditionDTO dto) {
        List<ClientAndAccountSubjectLedgerShowDetailDTO> searchDtos = resolvedVoucherRepository.clientAndAccountSubjectLedgerDetail(dto);

        BigDecimal totalSumPreviousCash = BigDecimal.ZERO;
        BigDecimal totalSumDebitAmount = BigDecimal.ZERO;
        BigDecimal totalSumCreditAmount = BigDecimal.ZERO;
        BigDecimal totalSumTotalCash = BigDecimal.ZERO;

        for (ClientAndAccountSubjectLedgerShowDetailDTO oneDto : searchDtos) {
            BigDecimal totalCash = oneDto.getTotalDebitAmount().subtract(oneDto.getTotalCreditAmount());
            oneDto.setTotalCashAmount(totalCash);
            totalSumDebitAmount = totalSumDebitAmount.add(oneDto.getTotalDebitAmount());
            totalSumCreditAmount = totalSumCreditAmount.add(oneDto.getTotalCreditAmount());
            totalSumTotalCash = totalSumTotalCash.add(totalCash);
        }
        return ClientAndAccountSubjectLedgerShowDetailAllDTO.create(
                searchDtos,
                totalSumPreviousCash,
                totalSumDebitAmount,
                totalSumCreditAmount,
                totalSumTotalCash);

    }
}
