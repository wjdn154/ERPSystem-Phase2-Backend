package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaxInvoiceLedgerServiceImpl implements TaxInvoiceLedgerService {

    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;

    @Override
    public Object show(TaxInvoiceLedgerSearchDTO dto) {
        List<TaxInvoiceLedgerShowDTO> resultList = resolvedSaleAndPurchaseVoucherRepository.showTaxInvoiceLedger(dto);

        Map<String, List<TaxInvoiceLedgerShowDTO>> resultMap = resultList.stream()
                .collect(Collectors.groupingBy(TaxInvoiceLedgerShowDTO::getClientCode));

        Map<String, List<TaxInvoiceLedgerShowDTO>> groupedData = resultList.stream()
                .collect(Collectors.groupingBy(dto2 -> dto2.getClientCode() + "-" + dto2.getClientName() + "-" + dto2.getClientNumber()));

        List<TaxInvoiceLedgerShowDTO> finalList = new ArrayList<>();

        for (Map.Entry<String, List<TaxInvoiceLedgerShowDTO>> entry : groupedData.entrySet()) {
            List<TaxInvoiceLedgerShowDTO> groupedList = entry.getValue();

            int totalVoucherCount = groupedList.stream().mapToInt(TaxInvoiceLedgerShowDTO::getVoucherCount).sum();
            BigDecimal totalSupplyAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getSupplyAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalVatAmount = groupedList.stream()
                    .map(TaxInvoiceLedgerShowDTO::getVatAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            TaxInvoiceLedgerShowDTO summaryDTO = new TaxInvoiceLedgerShowDTO(
                    groupedList.get(0).getClientCode(),
                    groupedList.get(0).getClientName(),
                    groupedList.get(0).getClientNumber(),
                    null,
                    totalVoucherCount,
                    totalSupplyAmount,
                    totalVatAmount
            );

            finalList.add(summaryDTO);
            finalList.addAll(groupedList);
        }
        return null;
    }
}
