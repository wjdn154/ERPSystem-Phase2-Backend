package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnresolvedVoucherDeleteDto {
    private LocalDate searchDate;
    private List<String> searchVoucherNumList;
    private Long ManagerId; // 담당자 ID
}
