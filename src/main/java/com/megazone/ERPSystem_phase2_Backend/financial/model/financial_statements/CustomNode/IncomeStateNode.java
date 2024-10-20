package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class IncomeStateNode implements Comparable<IncomeStateNode> {
    protected String name;
    protected int structureMin;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    protected List<IncomeStateNode> children = new ArrayList<>();

    public IncomeStateNode(String name, int structureMin) {
        this.name = name;
        this.structureMin = structureMin;
    }

    public void addChild(IncomeStateNode child) {
        children.add(child);
        Collections.sort(children);
    }

    public void addValues(IncomeStatementLedgerDTO data) {
        this.totalAmount = this.totalAmount.add(
                data.getAmount() != null ? data.getAmount() : BigDecimal.ZERO
        );
    }

    public String getLevel() {
        return this.getClass().getSimpleName().replace("Node", "");
    }

    @Override
    public int compareTo(IncomeStateNode other) {
        return Integer.compare(this.structureMin, other.structureMin);
    }
}
