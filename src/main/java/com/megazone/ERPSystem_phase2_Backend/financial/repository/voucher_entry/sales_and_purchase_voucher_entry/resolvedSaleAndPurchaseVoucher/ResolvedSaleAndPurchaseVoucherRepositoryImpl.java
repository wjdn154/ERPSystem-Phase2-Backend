package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QJournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QJournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QVatType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.ElectronicTaxInvoiceStatus;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class ResolvedSaleAndPurchaseVoucherRepositoryImpl implements ResolvedSaleAndPurchaseVoucherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SalesAndPurChaseLedgerShowDTO> SalesAndPurChaseLedgerShowList(SalesAndPurChaseLedgerSearchDTO dto) {
        QClient client = QClient.client;
        QVatType vatType = QVatType.vatType;
        QJournalEntry journalEntry = QJournalEntry.journalEntry;
        QEmployee employee = QEmployee.employee;
        QDepartment department = QDepartment.department;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;
        QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;
        QJournalEntryTypeSetup journalEntryTypeSetup = QJournalEntryTypeSetup.journalEntryTypeSetup;

        return queryFactory.select(
                        Projections.constructor(SalesAndPurChaseLedgerShowDTO.class,
                                voucher.id,
                                vatType.vatName,
                                vatType.transactionType,
                                accountSubject.code,
                                accountSubject.name,
                                voucher.voucherDate,
                                voucher.voucherNumber,
                                voucher.itemName,
                                voucher.supplyAmount,
                                voucher.vatAmount,
                                voucher.supplyAmount.add(voucher.vatAmount),
                                client.code,
                                client.printClientName,
                                voucher.electronicTaxInvoiceStatus,
                                journalEntry.name,
                                employee.employeeNumber,
                                department.departmentName,
                                employee.lastName.concat(employee.firstName)
                        ))
                .from(voucher)
                .join(journalEntry).on(journalEntry.id.eq(voucher.journalEntry.id))
                .join(journalEntryTypeSetup).on(journalEntryTypeSetup.id.eq(journalEntry.journalEntryTypeSetup.id))
                .join(accountSubject).on(accountSubject.id.eq(journalEntryTypeSetup.accountSubject.id))
                .join(client).on(client.id.eq(voucher.client.id))
                .join(vatType).on(vatType.id.eq(voucher.vatType.id))
                .join(journalEntry).on(journalEntry.id.eq(voucher.journalEntry.id))
                .join(employee).on(employee.id.eq(voucher.voucherManager.id))
                .join(department).on(employee.department.id.eq(department.id))
                .where(voucher.voucherDate.between(dto.getStartDate(),dto.getEndDate()))
                .orderBy(voucher.voucherDate.asc())
                .fetch();
    }
    // 없는달 제외 출력 로직
//    @Override
//    public List<TaxInvoiceLedgerShowDTO> showTaxInvoiceLedger(TaxInvoiceLedgerSearchDTO dto) {
//        QClient client = QClient.client;
//        QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;
//        QVatType vatType = QVatType.vatType;
//
//// 1월부터 12월까지의 월을 수동으로 생성 (QueryDSL로 With절이나 CROSS JOIN을 사용하지 못하므로)
//        List<Integer> allMonths = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
//
//// 결과 리스트를 담을 리스트
//        List<TaxInvoiceLedgerShowDTO> resultList = new ArrayList<>();
//
//// 모든 월을 돌면서 각 월에 대한 집계 데이터를 생성
//        for (Integer month : allMonths) {
//            List<TaxInvoiceLedgerShowDTO> monthlyResult = queryFactory
//                    .select(Projections.constructor(TaxInvoiceLedgerShowDTO.class,
//                            client.code.as("clientCode"),
//                            client.printClientName.as("clientName"),
//                            client.businessRegistrationNumber.as("clientNumber"),
//                            Expressions.asNumber(month).as("month"),
//                            Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", voucher.id.count()),
//                            voucher.supplyAmount.sum().as("supplyAmount"),
//                            voucher.vatAmount.sum().as("vatAmount")
//                    ))
//                    .from(voucher)
//                    .join(client).on(voucher.client.id.eq(client.id))
//                    .join(vatType).on(voucher.vatType.id.eq(vatType.id))
//                    .where(
//                            voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
//                                    .and(voucher.electronicTaxInvoiceStatus.eq(ElectronicTaxInvoiceStatus.PUBLISHED))
//                                    .and(client.code.castToNum(Integer.class).between(
//                                            Integer.parseInt(dto.getStartClientCode()), Integer.parseInt(dto.getEndClientCode())))
//                                    .and(voucher.voucherDate.month().eq(month))
//                                    .and(vatType.transactionType.eq(dto.getTransactionType()))
//                    )
//                    .groupBy(client.code)
//                    .orderBy(client.code.asc())
//                    .fetch();
//
//            // 해당 월에 데이터가 없는 경우, 빈 값을 추가
//            if (monthlyResult.isEmpty()) {
//                List<TaxInvoiceLedgerShowDTO> clients = queryFactory
//                        .select(Projections.constructor(TaxInvoiceLedgerShowDTO.class,
//                                client.code.as("clientCode"),
//                                client.printClientName.as("clientName"),
//                                client.businessRegistrationNumber.as("clientNumber"),
//                                Expressions.asNumber(month),  // 월을 수동으로 추가
//                                Expressions.constant(0),
//                                Expressions.constant(BigDecimal.ZERO),
//                                Expressions.constant(BigDecimal.ZERO)
//                        ))
//                        .from(client)
//                        .where(client.code.castToNum(Integer.class).between(
//                                Integer.parseInt(dto.getStartClientCode()), Integer.parseInt(dto.getEndClientCode())))
//                        .fetch();
//
//                resultList.addAll(clients);
//            } else {
//                resultList.addAll(monthlyResult);
//            }
//        }
//
//        return resultList;
//    }
//}
    //없는달 포함 출력 로직
@Override
public List<TaxInvoiceLedgerShowDTO> showTaxInvoiceLedger(TaxInvoiceLedgerSearchDTO dto) {
    QClient client = QClient.client;
    QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;
    QVatType vatType = QVatType.vatType;

// 1월부터 12월까지의 월을 수동으로 생성 (QueryDSL로 With절이나 CROSS JOIN을 사용하지 못하므로)
    List<Integer> allMonths = IntStream.rangeClosed(dto.getStartDate().getMonthValue(), dto.getEndDate().getMonthValue()).boxed().collect(Collectors.toList());

// 모든 클라이언트를 미리 조회
    List<TaxInvoiceLedgerShowDTO> allClients = queryFactory
            .select(Projections.constructor(TaxInvoiceLedgerShowDTO.class,
                    client.code.as("clientCode"),
                    client.printClientName.as("clientName"),
                    client.businessRegistrationNumber.as("clientNumber"),
                    Expressions.asNumber(1),  // 월을 임의로 추가 (나중에 대체됨)
                    Expressions.constant(0),  // 임시 값
                    Expressions.constant(BigDecimal.ZERO),  // 임시 값
                    Expressions.constant(BigDecimal.ZERO)  // 임시 값
            ))
            .from(client)
            .where(client.code.castToNum(Integer.class).between(
                    Integer.parseInt(dto.getStartClientCode()), Integer.parseInt(dto.getEndClientCode())))
            .fetch();

// 결과 리스트를 담을 리스트
    List<TaxInvoiceLedgerShowDTO> resultList = new ArrayList<>();

// 모든 클라이언트에 대해 모든 월을 돌면서 각 월에 대한 집계 데이터를 생성
    for (TaxInvoiceLedgerShowDTO clientInfo : allClients) {
        for (Integer month : allMonths) {
            List<TaxInvoiceLedgerShowDTO> monthlyResult = queryFactory
                    .select(Projections.constructor(TaxInvoiceLedgerShowDTO.class,
                            client.code.as("clientCode"),
                            client.printClientName.as("clientName"),
                            client.businessRegistrationNumber.as("clientNumber"),
                            Expressions.asNumber(month).as("month"),
                            Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", voucher.id.count()),
                            voucher.supplyAmount.sum().as("supplyAmount"),
                            voucher.vatAmount.sum().as("vatAmount")
                    ))
                    .from(voucher)
                    .join(client).on(voucher.client.id.eq(client.id))
                    .join(vatType).on(voucher.vatType.id.eq(vatType.id))
                    .where(
                            voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                                    .and(voucher.electronicTaxInvoiceStatus.eq(ElectronicTaxInvoiceStatus.PUBLISHED))
                                    .and(client.code.eq(clientInfo.getClientCode()))  // 특정 클라이언트로 제한
                                    .and(voucher.voucherDate.month().eq(month))
                                    .and(vatType.transactionType.eq(dto.getTransactionType()))
                    )
                    .groupBy(client.code)
                    .orderBy(client.code.asc())
                    .fetch();

            // 해당 월에 데이터가 없는 경우, 빈 값을 추가
            if (monthlyResult.isEmpty()) {
                TaxInvoiceLedgerShowDTO emptyResult = new TaxInvoiceLedgerShowDTO(
                        clientInfo.getClientCode(),
                        clientInfo.getClientName(),
                        clientInfo.getClientNumber(),
                        month,
                        0,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                );
                resultList.add(emptyResult);
            } else {
                resultList.addAll(monthlyResult);
            }
        }
    }

    return resultList;

}
}
