package com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode;

public class IncomeStatementAccountNode extends IncomeStateNode {
    public IncomeStatementAccountNode(String name, int structureMin) {
        super(name, structureMin);
    }

    @Override
    public void addChild(IncomeStateNode child) {
        // 리프 노드이므로 자식이 없음
    }
}
