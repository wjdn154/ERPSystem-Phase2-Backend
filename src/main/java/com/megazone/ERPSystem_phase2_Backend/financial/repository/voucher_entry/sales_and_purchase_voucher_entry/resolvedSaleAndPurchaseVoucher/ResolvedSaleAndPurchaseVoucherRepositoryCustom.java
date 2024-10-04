package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;

import java.util.List;

public interface ResolvedSaleAndPurchaseVoucherRepositoryCustom {
    List<SalesAndPurChaseLedgerShowDTO> SalesAndPurChaseLedgerShowList(SalesAndPurChaseLedgerSearchDTO dto);

    Object showTaxInvoiceLedger(TaxInvoiceLedgerSearchDTO dto);
}
