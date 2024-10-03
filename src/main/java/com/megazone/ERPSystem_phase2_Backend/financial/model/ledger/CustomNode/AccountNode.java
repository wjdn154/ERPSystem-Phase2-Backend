package com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.CustomNode;

public class AccountNode extends CustomNode {
    public AccountNode(String name, int structureMin) {
        super(name, structureMin);
    }

    @Override
    public void addChild(CustomNode child) {
        // 리프 노드이므로 자식이 없음
    }
}
