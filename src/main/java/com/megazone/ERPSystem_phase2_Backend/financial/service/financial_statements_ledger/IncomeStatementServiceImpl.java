package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.IncomeStateNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.IncomeStatementAccountNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.IncomeStatementMediumCategoryNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.IncomeStatementSmallCategoryNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeStatementServiceImpl implements IncomeStatementService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

    @Override
    public List<IncomeStatementLedgerShowDTO> show(IncomeStatementSearchDTO dto) {
        // 1. 쿼리 결과 가져오기
        List<IncomeStatementLedgerDTO> queryResults = resolvedVoucherRepository.incomeStatementShow(dto);

        // 2. 트리 구조 생성
        Map<String, IncomeStatementMediumCategoryNode> root = new LinkedHashMap<>();

        for (IncomeStatementLedgerDTO data : queryResults) {
            String mediumCategoryName = data.getMediumCategory(); // 중분류 이름
            String smallCategoryName = data.getSmallCategory();   // 소분류 이름
            String accountName = data.getFinancialStatementsName(); // 계정명
            int mediumStructureMin = data.getAccountStructureMin(); // 중분류 구조 최소값
            int smallStructureMin = Integer.parseInt(data.getFinancialStatementsCode()); // 소분류 구조 최소값

            // 3. 중분류 노드 가져오기 또는 생성
            IncomeStatementMediumCategoryNode mediumNode = root.get(mediumCategoryName);
            if (mediumNode == null) {
                mediumNode = new IncomeStatementMediumCategoryNode(mediumCategoryName, mediumStructureMin);
                root.put(mediumCategoryName, mediumNode);
            }
            mediumNode.addValues(data); // 중분류 노드에 값 추가

            // 4. 소분류 노드 가져오기 또는 생성
            IncomeStatementSmallCategoryNode smallNode = null;
            for (IncomeStateNode node : mediumNode.getChildren()) {
                if (node.getName().equals(smallCategoryName)) {
                    smallNode = (IncomeStatementSmallCategoryNode) node;
                    break;
                }
            }
            if (smallNode == null) {
                smallNode = new IncomeStatementSmallCategoryNode(smallCategoryName, smallStructureMin);
                mediumNode.addChild(smallNode);
            }
            smallNode.addValues(data); // 소분류 노드에 값 추가

            // 5. 계정 노드 생성 및 추가
            IncomeStatementAccountNode accountNode = new IncomeStatementAccountNode(accountName, data.getFinancialStatementId().intValue());
            accountNode.addValues(data);
            smallNode.addChild(accountNode);
        }

        // 6. 트리를 순회하여 IncomeStatementLedgerShowDTO 리스트로 변환
        List<IncomeStatementLedgerShowDTO> result = new ArrayList<>();
        List<IncomeStatementMediumCategoryNode> sortedMediumNodes = new ArrayList<>(root.values());
        Collections.sort(sortedMediumNodes);

        for (IncomeStatementMediumCategoryNode mediumNode : sortedMediumNodes) {
            result.add(IncomeStatementLedgerShowDTO.create(mediumNode, "Medium_Category"));

            List<IncomeStateNode> sortedSmallNodes = mediumNode.getChildren();
            for (IncomeStateNode smallNode : sortedSmallNodes) {
                result.add(IncomeStatementLedgerShowDTO.create(smallNode, "Small_Category"));

                List<IncomeStateNode> sortedAccountNodes = smallNode.getChildren();
                for (IncomeStateNode accountNode : sortedAccountNodes) {
                    result.add(IncomeStatementLedgerShowDTO.create(accountNode, "Account_Name"));
                }
            }
        }

        return result;
    }
}
