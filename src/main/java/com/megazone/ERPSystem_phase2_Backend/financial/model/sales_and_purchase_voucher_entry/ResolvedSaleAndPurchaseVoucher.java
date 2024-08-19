package com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "resolved_sale_and_purchase_voucher")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resolved_sale_and_purchase_voucher")
public class ResolvedSaleAndPurchaseVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
