package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.ClientLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ClientLedgerServiceImpl implements ClientLedgerService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public ClientLedgerShowAllDTO show(ClientLedgerSearchDTO dto) {
        List<ClientLedgerShowDTO> clientLedgerShowDTOS = resolvedVoucherRepository.clientLedgerList(dto);
        BigDecimal totalSumPreviousCash = BigDecimal.ZERO;
        BigDecimal totalSumDebitAmount = BigDecimal.ZERO;
        BigDecimal totalSumCreditAmount = BigDecimal.ZERO;
        BigDecimal totalSumTotalCash = BigDecimal.ZERO;

        for (ClientLedgerShowDTO clientLedgerShowDTO : clientLedgerShowDTOS) {
            BigDecimal totalCash = clientLedgerShowDTO.getDebitTotalAmount().subtract(clientLedgerShowDTO.getCreditTotalAmount());
            clientLedgerShowDTO.setCashTotalAmount(totalCash);
            totalSumDebitAmount = totalSumDebitAmount.add(clientLedgerShowDTO.getDebitTotalAmount());
            totalSumCreditAmount = totalSumCreditAmount.add(clientLedgerShowDTO.getCreditTotalAmount());
            totalSumTotalCash = totalSumTotalCash.add(totalCash);
        }
        return ClientLedgerShowAllDTO.create(
                clientLedgerShowDTOS,
                totalSumPreviousCash,
                totalSumDebitAmount,
                totalSumCreditAmount,
                totalSumTotalCash);

    }
}
