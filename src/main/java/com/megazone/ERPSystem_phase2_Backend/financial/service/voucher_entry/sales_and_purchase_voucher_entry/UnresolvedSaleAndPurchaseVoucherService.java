package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherApprovalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public interface UnresolvedSaleAndPurchaseVoucherService {
    UnresolvedSaleAndPurchaseVoucher save(UnresolvedSaleAndPurchaseVoucherEntryDTO dto);

//    String CreateVoucherNumber(LocalDate voucherDate);

    List<UnresolvedSaleAndPurchaseVoucher> searchAllVoucher(LocalDate date);

    String deleteVoucher(UnresolvedSaleAndPurchaseVoucherDeleteDTO dto);

    BigDecimal totalDebit(List<UnresolvedVoucher> vouchers);

    BigDecimal totalCredit(List<UnresolvedVoucher> vouchers);

    List<UnresolvedVoucher> searchVoucher(String voucherNumber);

    BigDecimal calculateTotalAmount(List<UnresolvedVoucher> vouchers, Function<UnresolvedVoucher, BigDecimal> amount);

    List<UnresolvedSaleAndPurchaseVoucher> ApprovalProcessing(UnresolvedSaleAndPurchaseVoucherApprovalDTO dto);
}