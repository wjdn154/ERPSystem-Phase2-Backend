package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResolvedSaleAndPurchaseVoucherServiceImpl implements ResolvedSaleAndPurchaseVoucherService {

    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;
    private final ResolvedVoucherRepository resolvedVoucherRepository;







    /**
     * 미결전표가 승인되었을때 일반전표가 등록되는 메소드
     * @param unresolvedVoucherList 승인된 미결전표 List
     * @return 생성된 일반전표 반환
     */
    @Override
    public void resolvedVoucherEntry(List<UnresolvedSaleAndPurchaseVoucher> unresolvedVoucherList) {
        List<ResolvedSaleAndPurchaseVoucher> resolvedVoucherList = new ArrayList<>();
        LocalDateTime nowTime = LocalDateTime.now();
        unresolvedVoucherList
                .forEach(unresolvedVoucher -> {
                    ResolvedSaleAndPurchaseVoucher resolvedVoucher = createResolvedVoucher(unresolvedVoucher,nowTime);
                    resolvedVoucherList.add(resolvedSaleAndPurchaseVoucherRepository.save(resolvedVoucher));
                });
    }

    private ResolvedSaleAndPurchaseVoucher createResolvedVoucher(UnresolvedSaleAndPurchaseVoucher unresolvedVoucher, LocalDateTime nowTime) {
        List<ResolvedVoucher> ResolvedVouchers = unresolvedVoucher.getUnresolvedVouchers().stream()
                .map(voucher -> {return ResolvedVoucher.builder()
                        .accountSubject(voucher.getAccountSubject())
                        .transactionDescription(voucher.getTransactionDescription())
                        .voucherNumber(voucher.getVoucherNumber())
                        .voucherType(voucher.getVoucherType())
                        .debitAmount(voucher.getDebitAmount())
                        .creditAmount(voucher.getCreditAmount())
                        .voucherDate(voucher.getVoucherDate())
                        .voucherApprovalTime(nowTime)
                        .build();}).toList();

        return ResolvedSaleAndPurchaseVoucher.builder()
                .vatType(unresolvedVoucher.getVatType())
                .journalEntry(unresolvedVoucher.getJournalEntry())
                .unresolvedVouchers(ResolvedVouchers)
                .voucherNumber(unresolvedVoucher.getVoucherNumber())
                .voucherDate(unresolvedVoucher.getVoucherDate())
                .itemName(unresolvedVoucher.getItemName())
                .quantity(unresolvedVoucher.getQuantity())
                .unitPrice(unresolvedVoucher.getUnitPrice())
                .supplyAmount(unresolvedVoucher.getSupplyAmount())
                .vatAmount(unresolvedVoucher.getVatAmount())
                .electronicTaxInvoiceStatus(unresolvedVoucher.getElectronicTaxInvoiceStatus())
                .voucherApproveTime(nowTime)
                .build();


    }
}
