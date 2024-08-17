package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto;

import java.time.LocalDate;
import java.util.List;

public class ResolvedVoucherDeleteDTO {
    private LocalDate searchDate;
    private List<String> searchVoucherNumList;
    private Long ApproveManagerId; // 담당자 ID
}
