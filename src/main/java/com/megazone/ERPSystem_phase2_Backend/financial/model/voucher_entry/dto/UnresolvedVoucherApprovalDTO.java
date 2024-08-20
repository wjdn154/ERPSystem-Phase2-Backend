package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnresolvedVoucherApprovalDTO {
    private LocalDate searchDate;
    private List<String> searchVoucherNumList;
    private Long approvalManagerId; // 담당자 ID
    private ApprovalStatus approvalStatus;
}
