package com.megazone.ERPSystem_phase2_Backend.financial.service.financial_statements_ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateAccountNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateMediumCategoryNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.CustomNode.FinancialStateSmallCategoryNode;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancialStatementsLedgerServiceImpl implements FinancialStatementsLedgerService {

    private final ResolvedVoucherRepository resolvedVoucherRepository;


    /**
     * 표준재무재표 출력 로직
     * 자본총계, 부채총계, 합계 방식 필요
     */
    @Override
    public Object show(FinancialStatementsLedgerSearchDTO dto) {
        List<FinancialStatementsLedgerDTO> queryResults = resolvedVoucherRepository.financialStatementsShow(dto);
        // 트리 구조 생성
        Map<String, FinancialStateMediumCategoryNode> root = new LinkedHashMap<>();

        for (FinancialStatementsLedgerDTO data : queryResults) {
            String mediumCategoryName = data.getMediumCategory(); // 중분류 카테고리
            String smallCategoryName = data.getSmallCategory();   // 소분류 카테고리
            String statementsName = data.getFinancialStatementsName(); // 계정명
            int mediumStructureMin = data.getAccountStructureMin();    // 중분류의 구조 최소 값
            int smallStructureMin = Integer.parseInt(data.getAccountStructureCode()); // 소분류의 구조 최소 값
            int accountStructureMin = Integer.parseInt(data.getFinancialStatementsCode()); // 계정 구조 코드

            // 중분류와 소분류가 동일한 경우 처리
            if (mediumCategoryName.equals(smallCategoryName)) {
                smallCategoryName = null;
                smallStructureMin = mediumStructureMin;
            }

            // AssetCategoryNode 가져오기 또는 생성
            FinancialStateMediumCategoryNode mediumNode = root.get(mediumCategoryName);
            if (mediumNode == null) {
                mediumNode = new FinancialStateMediumCategoryNode(mediumCategoryName, mediumStructureMin);
                root.put(mediumCategoryName, mediumNode);
            }

            // AssetCategoryNode에 합계 추가
            mediumNode.addValues(data);

            FinancialStateNode parentNode;

            if (smallCategoryName != null) {
                // SubAssetCategoryNode 가져오기 또는 생성
                FinancialStateSmallCategoryNode smallNode = null;
                for (FinancialStateNode node : mediumNode.getChildren()) {
                    if (node.getName().equals(smallCategoryName)) {
                        smallNode = (FinancialStateSmallCategoryNode) node;
                        break;
                    }
                }
                if (smallNode == null) {
                    smallNode = new FinancialStateSmallCategoryNode(smallCategoryName, smallStructureMin);
                    mediumNode.addChild(smallNode);
                }
                // SubAssetCategoryNode에 합계 추가
                smallNode.addValues(data);
                parentNode = smallNode;
            } else {
                // 소분류가 없으면 중분류 노드가 부모 노드
                parentNode = mediumNode;
            }

            // 계정 노드 생성 및 추가
            FinancialStateAccountNode accountNode = new FinancialStateAccountNode(statementsName, accountStructureMin);
            accountNode.addValues(data);
            parentNode.addChild(accountNode);
        }

        // 트리를 순회하여 FinancialStatementsLedgerShowDTO 리스트로 변환
        List<FinancialStatementsLedgerShowDTO> result = new ArrayList<>();
        List<FinancialStateMediumCategoryNode> sortedMediumNodes = new ArrayList<>(root.values());
        Collections.sort(sortedMediumNodes);

        for (FinancialStateMediumCategoryNode mediumNode : sortedMediumNodes) {
            result.add(FinancialStatementsLedgerShowDTO.create(mediumNode, "Medium_category"));


            List<FinancialStateNode> sortedSmallNodes = mediumNode.getChildren();
            for (FinancialStateNode childNode : sortedSmallNodes) {
                if (childNode instanceof FinancialStateSmallCategoryNode) {
                    result.add(FinancialStatementsLedgerShowDTO.create(childNode, "Small_category"));
                    List<FinancialStateNode> sortedAccountNodes = childNode.getChildren();
                    for (FinancialStateNode accountNode : sortedAccountNodes) {
                        result.add(FinancialStatementsLedgerShowDTO.create(accountNode, "Account_name"));
                    }
                } else if (childNode instanceof FinancialStateAccountNode) {
                    result.add(FinancialStatementsLedgerShowDTO.create(childNode, "Account_name"));
                }
            }
        }
        // 결과 반환
        return result;
    }
}

