package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;

import java.math.BigDecimal;

public interface VatTypeService {
    BigDecimal vatAmountCalculate(VatAmountWithQuantityPriceDTO dto);
    BigDecimal vatAmountCalculate(VatAmountWithSupplyAmountDTO dto);

    Long vatTypeGetId(String vatTypeId);

    VatTypeShowDTO vatTypeGet(Long vatTypeId);
}
