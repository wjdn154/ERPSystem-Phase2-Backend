package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.CustomNode.CustomNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyAndMonthJournalShowDTO {
    private String level; // "Medium_category", "Small_category", "Account_name"
    private String name;
    private BigDecimal cashTotalDebit;
    private BigDecimal subTotalDebit;
    private BigDecimal sumTotalDebit;
    private BigDecimal cashTotalCredit;
    private BigDecimal subTotalCredit;
    private BigDecimal sumTotalCredit;

    public static DailyAndMonthJournalShowDTO create(CustomNode node, String level) {
        return new DailyAndMonthJournalShowDTO(
                level,
                node.getName(),
                node.getCashTotalDebit(),
                node.getSubTotalDebit(),
                node.getSumTotalDebit(),
                node.getCashTotalCredit(),
                node.getSubTotalCredit(),
                node.getSumTotalCredit()
        );
    }
}
