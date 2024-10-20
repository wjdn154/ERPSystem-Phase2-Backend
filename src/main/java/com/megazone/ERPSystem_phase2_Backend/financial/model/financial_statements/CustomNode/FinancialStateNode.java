package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class FinancialStateNode implements Comparable<FinancialStateNode> {
    protected String name;
    protected int structureMin;
    private BigDecimal totalDebitBalance = BigDecimal.ZERO;
    private BigDecimal totalDebitAmount = BigDecimal.ZERO;
    private BigDecimal totalCreditBalance = BigDecimal.ZERO;
    private BigDecimal totalCreditAmount = BigDecimal.ZERO;
    protected List<FinancialStateNode> children = new ArrayList<>();

    public FinancialStateNode(String name, int structureMin) {
        this.name = name;
        this.structureMin = structureMin;
    }

    public void addChild(FinancialStateNode child) {
        children.add(child);
        Collections.sort(children);
    }

    public void addValues(FinancialStatementsLedgerDTO data) {
        this.totalDebitBalance = this.totalDebitBalance.add(
                data.getTotalDebitBalance() != null ? data.getTotalDebitBalance() : BigDecimal.ZERO
        );
        this.totalDebitAmount = this.totalDebitAmount.add(
                data.getTotalDebitAmount() != null ? data.getTotalDebitAmount() : BigDecimal.ZERO
        );
        this.totalCreditBalance = this.totalCreditBalance.add(
                data.getTotalCreditBalance() != null ? data.getTotalCreditBalance() : BigDecimal.ZERO
        );
        this.totalCreditAmount = this.totalCreditAmount.add(
                data.getTotalCreditAmount() != null ? data.getTotalCreditAmount() : BigDecimal.ZERO
        );
    }

    public String getLevel() {
        return this.getClass().getSimpleName().replace("Node", "");
    }

    @Override
    public int compareTo(FinancialStateNode other) {
        return Integer.compare(this.structureMin, other.structureMin);
    }
}
