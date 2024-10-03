package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.CustomNode;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalDTO;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public abstract class CustomNode implements Comparable<CustomNode> {
    protected String name;
    protected int structureMin;
    protected BigDecimal cashTotalDebit = BigDecimal.ZERO;
    protected BigDecimal cashTotalCredit = BigDecimal.ZERO;
    protected BigDecimal subTotalDebit = BigDecimal.ZERO;
    protected BigDecimal subTotalCredit = BigDecimal.ZERO;
    protected BigDecimal sumTotalDebit = BigDecimal.ZERO;
    protected BigDecimal sumTotalCredit = BigDecimal.ZERO;
    protected List<CustomNode> children = new ArrayList<>();

    public CustomNode(String name, int structureMin) {
        this.name = name;
        this.structureMin = structureMin;
    }

    public void addChild(CustomNode child) {
        children.add(child);
        Collections.sort(children);
    }

    public void addValues(DailyAndMonthJournalDTO data) {
        this.cashTotalDebit = this.cashTotalDebit.add(
                data.getCashTotalDebit() != null ? data.getCashTotalDebit() : BigDecimal.ZERO);
        this.cashTotalCredit = this.cashTotalCredit.add(
                data.getCashTotalCredit() != null ? data.getCashTotalCredit() : BigDecimal.ZERO);
        this.subTotalDebit = this.subTotalDebit.add(
                data.getSubTotalDebit() != null ? data.getSubTotalDebit() : BigDecimal.ZERO);
        this.subTotalCredit = this.subTotalCredit.add(
                data.getSubTotalCredit() != null ? data.getSubTotalCredit() : BigDecimal.ZERO);
        this.sumTotalDebit = this.sumTotalDebit.add(
                data.getSumTotalDebit() != null ? data.getSumTotalDebit() : BigDecimal.ZERO);
        this.sumTotalCredit = this.sumTotalCredit.add(
                data.getSumTotalCredit() != null ? data.getSumTotalCredit() : BigDecimal.ZERO);
    }

    public String getLevel() {
        return this.getClass().getSimpleName().replace("Node", "");
    }

    @Override
    public int compareTo(CustomNode other) {
        return Integer.compare(this.structureMin, other.structureMin);
    }

}
