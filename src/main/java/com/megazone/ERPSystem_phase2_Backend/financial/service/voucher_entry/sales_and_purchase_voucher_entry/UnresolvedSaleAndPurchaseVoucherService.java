package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface UnresolvedSaleAndPurchaseVoucherService {
    UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto);

    String CreateVoucherNumber(LocalDate voucherDate);
}
