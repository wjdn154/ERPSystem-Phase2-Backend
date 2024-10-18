package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.ResolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher.ResolvedSaleAndPurchaseVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ResolvedSaleAndPurchaseVoucherServiceImpl implements ResolvedSaleAndPurchaseVoucherService {

    private final ResolvedSaleAndPurchaseVoucherRepository resolvedSaleAndPurchaseVoucherRepository;
    @Override
    public List<ResolvedSaleAndPurchaseVoucher> searchAllVoucher(LocalDate date) {
        List<ResolvedSaleAndPurchaseVoucher> voucherList = new ArrayList<ResolvedSaleAndPurchaseVoucher>();

        try {
            voucherList = resolvedSaleAndPurchaseVoucherRepository.findByVoucherDateOrderByVoucherNumberAsc(date);
            if(voucherList.isEmpty()) {
                throw new NoSuchElementException("해당 날짜에 등록된 미결전표가 없습니다.");
            }
        }
        catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return voucherList;
    }

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

    @Override
    public List<ResolvedVoucher> searchEntryVoucher(Long voucherNumber) {
        return resolvedSaleAndPurchaseVoucherRepository.findByVoucherNumber(voucherNumber).getResolvedVouchers();
    }

    @Override
    public BigDecimal calculateTotalAmount(List<ResolvedVoucher> vouchers, Function<ResolvedVoucher, BigDecimal> amount) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ResolvedVoucher voucher : vouchers) {
            totalAmount = totalAmount.add(amount.apply(voucher));
        }

        return totalAmount;
    }

    @Override
    public BigDecimal totalDebit(List<ResolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, ResolvedVoucher::getDebitAmount);
    }
    @Override
    public BigDecimal totalCredit(List<ResolvedVoucher> vouchers) {
        return calculateTotalAmount(vouchers, ResolvedVoucher::getCreditAmount);
    }

    @Override
    public String deleteVoucher(ResolvedSaleAndPurchaseVoucherDeleteDTO dto) {
        try {
            if(true) { // 전표의 담당자 이거나, 승인권자면 삭제가능 << 기능구현 필요
                dto.getSearchVoucherNumList().forEach((voucherNumber) -> {
                    resolvedSaleAndPurchaseVoucherRepository.deleteByVoucherNumberAndVoucherDate(
                            voucherNumber, dto.getSearchDate());});
            }
        }
        catch (Exception e) {
            e.getStackTrace();
            return null;
        }
        return "미결 매출매입전표 삭제 성공";
    }

    private ResolvedSaleAndPurchaseVoucher createResolvedVoucher(UnresolvedSaleAndPurchaseVoucher unresolvedVoucher, LocalDateTime nowTime) {
        List<ResolvedVoucher> ResolvedVouchers = unresolvedVoucher.getUnresolvedVouchers().stream()
                .map(voucher -> {return ResolvedVoucher.builder()
                        .accountSubject(voucher.getAccountSubject())
                        .transactionDescription(voucher.getTransactionDescription())
                        .voucherNumber(voucher.getVoucherNumber())
                        .voucherManager(voucher.getVoucherManager())
                        .voucherType(voucher.getVoucherType())
                        .debitAmount(voucher.getDebitAmount())
                        .creditAmount(voucher.getCreditAmount())
                        .voucherDate(voucher.getVoucherDate())
                        .client(unresolvedVoucher.getClient())
                        .voucherApprovalTime(nowTime)
                        .voucherKind(VoucherKind.SALE_AND_PURCHASE)
                        .build();}).toList();

        return ResolvedSaleAndPurchaseVoucher.builder()
                .client(unresolvedVoucher.getClient())
                .vatType(unresolvedVoucher.getVatType())
                .voucherManager(unresolvedVoucher.getVoucherManager())
                .journalEntry(unresolvedVoucher.getJournalEntry())
                .resolvedVouchers(ResolvedVouchers)
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
