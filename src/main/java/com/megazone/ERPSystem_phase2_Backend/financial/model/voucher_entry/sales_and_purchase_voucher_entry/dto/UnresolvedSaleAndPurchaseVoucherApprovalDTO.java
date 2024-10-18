package com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tools.ant.taskdefs.Local;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnresolvedSaleAndPurchaseVoucherApprovalDTO {
    private LocalDate searchDate;
    private List<String> searchVoucherNumberList;
    private Long approvalManagerId; // 승인권자 ID
    private ApprovalStatus approvalStatus;
    private boolean superManager; // 총관리자 여부

    public String approvalResult() {
        String approvalResult = "";
        switch (approvalStatus) {
            case APPROVED:
                approvalResult = "승인";
                break;
            case REJECTED:
                approvalResult = "반려";
                break;
        }
        return approvalResult;
    }
}