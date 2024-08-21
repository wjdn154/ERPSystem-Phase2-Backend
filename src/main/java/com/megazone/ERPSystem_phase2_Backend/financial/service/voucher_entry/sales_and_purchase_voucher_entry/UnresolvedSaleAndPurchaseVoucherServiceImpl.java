package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class UnresolvedSaleAndPurchaseVoucherServiceImpl implements UnresolvedSaleAndPurchaseVoucherService {

    @Override
    public UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        return create(dto);
    }

    public UnresolvedSaleAndPurchaseVoucher create(UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        BigDecimal supplyAmount = createSupplyAmount(dto.getQuantity(),dto.getUnitPrice());
        return UnresolvedSaleAndPurchaseVoucher.builder()
                .vatType(dto.getVatType())
                .journalEntry(dto.getJournalEntry())
                .voucherDate(dto.getVoucherDate())
                .itemName(dto.getItemName())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .supplyAmount(supplyAmount)
                .vatAmount(createVatAmount(supplyAmount,dto.getVatType().getTaxRate()))
                .electronicTaxInvoiceStatus(dto.getElectronicTaxInvoiceStatus())
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
