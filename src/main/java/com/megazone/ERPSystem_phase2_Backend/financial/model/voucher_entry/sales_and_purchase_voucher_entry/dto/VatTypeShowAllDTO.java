package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import lombok.Data;

import java.util.List;

@Data
public class VatTypeShowAllDTO {
    private List<VatTypeShowDTO> salesVatTypeShowDTO;
    private List<VatTypeShowDTO> purchaseVatTypeShowDTO;
}
