package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JournalShowDTO {
    private List<ResolvedVoucherShowDTO> resolvedVoucherShowAllDTO;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal totalVoucherCount;
    public static JournalShowDTO create(List<ResolvedVoucherShowDTO> dtos, BigDecimal totalDebit,
                                        BigDecimal totalCredit, BigDecimal totalVoucherCount) {
        return new JournalShowDTO(
                dtos,
                totalDebit,
                totalCredit,
                totalVoucherCount
        );
    }
}
