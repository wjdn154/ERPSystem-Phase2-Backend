package com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.sales_and_purchase_voucher_entry.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // 세금과목 코드

    private String vatName; // 세금과목 이름

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // 매출, 매입 타입

    private BigDecimal taxRate; // 세금과목 세율
//
//    public boolean isValidTaxRate() {
//        return taxRate.compareTo(BigDecimal.ZERO) >= 0 && taxRate.compareTo(new BigDecimal("100")) <= 0;
//    }
}
