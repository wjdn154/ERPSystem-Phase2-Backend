package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedVoucherDeleteDTO {
    private LocalDate searchDate;
    private List<Long> searchVoucherNumList;
    private Long ApproveManagerId; // 담당자 ID
}
