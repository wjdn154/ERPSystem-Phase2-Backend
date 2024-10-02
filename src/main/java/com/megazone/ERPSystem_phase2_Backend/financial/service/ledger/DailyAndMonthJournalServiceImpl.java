package com.megazone.ERPSystem_phase2_Backend.financial.service.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.DailyAndMonthJournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyAndMonthJournalServiceImpl implements DailyAndMonthJournalService {
    private final ResolvedVoucherRepository resolvedVoucherRepository;

//    @Override
//    public Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
//
//
//        List<DailyAndMonthJournalShowDTO> queryResults = resolvedVoucherRepository.dailyLedgerList(dto);
//
//        // 데이터 그룹화
//        Map<String, Map<String, List<DailyAndMonthJournalShowDTO>>> groupedData = new HashMap<>();
//
//        for (DailyAndMonthJournalShowDTO result : queryResults) {
//            String mediumCategory = result.getAccountStructureMediumCategory();
//            String smallCategory = result.getAccountStructureSmallCategory();
//
//            // Medium_category가 없으면 추가
//            groupedData.computeIfAbsent(mediumCategory, k -> new HashMap<>());
//
//            // Small_category가 없으면 추가
//            groupedData.get(mediumCategory).computeIfAbsent(smallCategory, k -> new ArrayList<>());
//
//            // Account_name에 대한 DTO 추가
//            groupedData.get(mediumCategory).get(smallCategory).add(result);
//        }
//
//        // 그룹화된 데이터 출력 및 합계 계산
//        for (Map.Entry<String, Map<String, List<DailyAndMonthJournalShowDTO>>> mediumEntry : groupedData.entrySet()) {
//            String mediumCategory = mediumEntry.getKey();
//            BigDecimal mediumCategoryDebitTotal = BigDecimal.ZERO;
//            BigDecimal mediumCategoryCreditTotal = BigDecimal.ZERO;
//
//            System.out.println("Medium Category: " + mediumCategory);
//
//            for (Map.Entry<String, List<DailyAndMonthJournalShowDTO>> smallEntry : mediumEntry.getValue().entrySet()) {
//                String smallCategory = smallEntry.getKey();
//                BigDecimal smallCategoryDebitTotal = BigDecimal.ZERO;
//                BigDecimal smallCategoryCreditTotal = BigDecimal.ZERO;
//
//                System.out.println("  Small Category: " + smallCategory);
//
//                for (DailyAndMonthJournalShowDTO result2 : smallEntry.getValue()) {
//                    System.out.println("    Account Name: " + result2.getAccountName() +
//                            ", Debit Total: " + result2.getSumTotalDebit() +
//                            ", Credit Total: " + result2.getSumTotalCredit());
//
//                    // Account_name의 debit, credit 합계 계산
//                    smallCategoryDebitTotal = smallCategoryDebitTotal.add(result2.getSumTotalDebit());
//                    smallCategoryCreditTotal = smallCategoryCreditTotal.add(result2.getSumTotalCredit());
//                }
//
//                // Small_category별 합계 출력
//                System.out.println("  Small Category Total: " +
//                        "Debit: " + smallCategoryDebitTotal +
//                        ", Credit: " + smallCategoryCreditTotal);
//
//                // Medium_category별 합계에 더하기
//                mediumCategoryDebitTotal = mediumCategoryDebitTotal.add(smallCategoryDebitTotal);
//                mediumCategoryCreditTotal = mediumCategoryCreditTotal.add(smallCategoryCreditTotal);
//            }
//
//            // Medium_category별 합계 출력
//            System.out.println("Medium Category Total: " +
//                    "Debit: " + mediumCategoryDebitTotal +
//                    ", Credit: " + mediumCategoryCreditTotal);
//        }
//        return null;
//    }

//    @Override
//    public Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
//        List<DailyAndMonthJournalShowDTO> queryResults = resolvedVoucherRepository.dailyLedgerList(dto);
//        Map<String, Map<String, List<DailyAndMonthJournalShowDTO>>> groupedData = new HashMap<>();
//
//        for (DailyAndMonthJournalShowDTO result : queryResults) {
//            String mediumCategory = result.getAccountStructureMediumCategory();
//            String smallCategory = result.getAccountStructureSmallCategory();
//
//            // Medium_category가 없으면 추가
//            groupedData.computeIfAbsent(mediumCategory, k -> new HashMap<>());
//
//            // Small_category가 없으면 추가
//            groupedData.get(mediumCategory).computeIfAbsent(smallCategory, k -> new ArrayList<>());
//
//            // Account_name에 대한 DTO 추가
//            groupedData.get(mediumCategory).get(smallCategory).add(result);
//        }
//
//        // 트리 구조로 데이터 출력 및 합계 계산
//        printTreeStructure(groupedData, 0);
//        return null;
//    }
//    private static void printTreeStructure(Map<String, Map<String, List<DailyAndMonthJournalShowDTO>>> groupedData, int level) {
//        for (Map.Entry<String, Map<String, List<DailyAndMonthJournalShowDTO>>> mediumEntry : groupedData.entrySet()) {
//            String mediumCategory = mediumEntry.getKey();
//            BigDecimal mediumCategoryDebitTotal = BigDecimal.ZERO;
//            BigDecimal mediumCategoryCreditTotal = BigDecimal.ZERO;
//
//            // Medium Category 출력
//            printIndented(mediumCategory, level);
//
//            for (Map.Entry<String, List<DailyAndMonthJournalShowDTO>> smallEntry : mediumEntry.getValue().entrySet()) {
//                String smallCategory = smallEntry.getKey();
//                String CategoryName = "";
//                BigDecimal smallCategoryDebitTotal = BigDecimal.ZERO;
//                BigDecimal smallCategoryCreditTotal = BigDecimal.ZERO;
//
//                // Small Category 출력
//                printIndented(smallCategory, level + 1);
//
//                for (DailyAndMonthJournalShowDTO dto : smallEntry.getValue()) {
//                    String accountInfo = String.format("Account Name: %s, Debit: %s, Credit: %s",
//                            dto.getAccountName(), dto.getSumTotalDebit(), dto.getSumTotalCredit());
//
//                    // Account Name 출력
//                    printIndented(accountInfo, level + 2);
//
//                    // Small Category 합계 계산
//                    smallCategoryDebitTotal = smallCategoryDebitTotal.add(dto.getSumTotalDebit());
//                    smallCategoryCreditTotal = smallCategoryCreditTotal.add(dto.getSumTotalCredit());
//                }
//
//                // Small Category별 합계 출력
//                String smallCategoryTotal = String.format("Small Category Total: %s Debit: %s, Credit: %s",
//                        smallCategoryDebitTotal, smallCategoryCreditTotal);
//                printIndented(smallCategoryTotal, level + 1);
//
//                // Medium Category 합계에 더하기
//                mediumCategoryDebitTotal = mediumCategoryDebitTotal.add(smallCategoryDebitTotal);
//                mediumCategoryCreditTotal = mediumCategoryCreditTotal.add(smallCategoryCreditTotal);
//            }
//
//            // Medium Category별 합계 출력
//            String mediumCategoryTotal = String.format("Medium Category Total: Debit: %s, Credit: %s",
//                    mediumCategoryDebitTotal, mediumCategoryCreditTotal);
//            printIndented(mediumCategoryTotal, level);
//        }
//    }
//
//    // 들여쓰기를 적용하여 출력
//    private static void printIndented(String message, int level) {
//        String indent = "  ".repeat(level);
//        System.out.println(indent + message);
//    }

    @Override
    public Object dailyLedgerShow(DailyAndMonthJournalSearchDTO dto) {
        List<DailyAndMonthJournalShowDTO> queryResults = resolvedVoucherRepository.dailyLedgerList(dto);
        Map<String, Map<String, List<DailyAndMonthJournalShowDTO>>> groupedData = new HashMap<>();

        for (DailyAndMonthJournalShowDTO result : queryResults) {
            String mediumCategory = result.getAccountStructureMediumCategory();
            String smallCategory = result.getAccountStructureSmallCategory();

            // Medium_category가 없으면 추가
            groupedData.computeIfAbsent(mediumCategory, k -> new HashMap<>());

            // Small_category가 없으면 추가
            groupedData.get(mediumCategory).computeIfAbsent(smallCategory, k -> new ArrayList<>());

            // Account_name에 대한 DTO 추가
            groupedData.get(mediumCategory).get(smallCategory).add(result);
        }

        // 트리 구조로 데이터 출력 및 합계 계산
        printTreeStructure(groupedData, 0);
        return null;
    }

    // 트리 구조로 데이터 출력
    private static void printTreeStructure(Map<String, Map<String, List<DailyAndMonthJournalShowDTO>>> groupedData, int level) {
        for (Map.Entry<String, Map<String, List<DailyAndMonthJournalShowDTO>>> mediumEntry : groupedData.entrySet()) {
            String mediumCategory = mediumEntry.getKey();
            BigDecimal mediumCategoryDebitTotal = BigDecimal.ZERO;
            BigDecimal mediumCategoryCreditTotal = BigDecimal.ZERO;

            // Medium Category 출력
            printIndented(mediumCategory, level);

            for (Map.Entry<String, List<DailyAndMonthJournalShowDTO>> smallEntry : mediumEntry.getValue().entrySet()) {
                String smallCategory = smallEntry.getKey();
                BigDecimal smallCategoryDebitTotal = BigDecimal.ZERO;
                BigDecimal smallCategoryCreditTotal = BigDecimal.ZERO;

                // Small Category 출력
                printIndented(smallCategory, level + 1);

                for (DailyAndMonthJournalShowDTO dto : smallEntry.getValue()) {
                    String accountInfo = String.format("Account Name: %s, CashTotalDebit: %s, SubTotalDebit: %s, SumTotalDebit: %s, CashTotalCredit: %s, SubTotalCredit: %s, SumTotalCredit: %s",
                            dto.getAccountName(), dto.getCashTotalDebit(), dto.getSubTotalDebit(), dto.getSumTotalDebit(),
                            dto.getCashTotalCredit(), dto.getSubTotalCredit(), dto.getSumTotalCredit());

                    // Account Name 및 관련 금액 정보 출력
                    printIndented(accountInfo, level + 2);

                    // Small Category 합계 계산
                    smallCategoryDebitTotal = smallCategoryDebitTotal.add(dto.getSumTotalDebit());
                    smallCategoryCreditTotal = smallCategoryCreditTotal.add(dto.getSumTotalCredit());
                }

                // Small Category별 합계 출력
                String smallCategoryTotal = String.format("Small Category Total: Debit: %s, Credit: %s",
                        smallCategoryDebitTotal, smallCategoryCreditTotal);
                printIndented(smallCategoryTotal, level + 1);

                // Medium Category 합계에 더하기
                mediumCategoryDebitTotal = mediumCategoryDebitTotal.add(smallCategoryDebitTotal);
                mediumCategoryCreditTotal = mediumCategoryCreditTotal.add(smallCategoryCreditTotal);
            }

            // Medium Category별 합계 출력
            String mediumCategoryTotal = String.format("Medium Category Total: Debit: %s, Credit: %s",
                    mediumCategoryDebitTotal, mediumCategoryCreditTotal);
            printIndented(mediumCategoryTotal, level);
        }
    }

    // 들여쓰기를 적용하여 출력
    private static void printIndented(String message, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + message);
    }
}
