package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.JorunalEntryTypeSetupRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.JournalEntryRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.unresolveSaleAndPurchaseVoucher.VatTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UnresolvedSaleAndPurchaseVoucherServiceImpl implements UnresolvedSaleAndPurchaseVoucherService {

    private final VatTypeRepository vatTypeRepository;
    private final JournalEntryRepository journalEntryRepository;
    private JorunalEntryTypeSetupRepository entryTypeSetupRepository;

    @Override
    public UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        return create(dto);
    }

    public UnresolvedSaleAndPurchaseVoucher create(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        BigDecimal supplyAmount = createSupplyAmount(dto.getQuantity(),dto.getUnitPrice());

        VatType vatType = vatTypeRepository.findByCode(dto.getVatTypeCode()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 코드의 세금과목이 없습니다."));

        JournalEntry journalEntry = journalEntryRepository.findByCodeAndTransactionType(dto.getJournalEntryCode(),vatType.getTransactionType()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 코드의 분개유형이 없습니다."));

        return UnresolvedSaleAndPurchaseVoucher.builder()
                .vatType(vatType)
                .journalEntry(journalEntry)
                .voucherDate(dto.getVoucherDate())
                .itemName(dto.getItemName())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .supplyAmount(supplyAmount)
                .vatAmount(createVatAmount(supplyAmount,vatType.getTaxRate()))
                .electronicTaxInvoiceStatus(ElectronicTaxInvoiceStatus.UNPUBLISHED)
                .voucherRegistrationTime(LocalDateTime.now())
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
    }

    public BigDecimal createSupplyAmount(BigDecimal quantity, BigDecimal unitPrice) {
        return quantity.multiply(unitPrice);
    }

    public BigDecimal createVatAmount(BigDecimal supplyAmount, BigDecimal vatRate) {
        return supplyAmount.multiply(vatRate);
    }
}
