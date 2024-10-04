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

    @Override
    public List<TaxInvoiceLedgerShowDTO> showTaxInvoiceLedger(TaxInvoiceLedgerSearchDTO dto) {
        QClient client = QClient.client;
        QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;
        QVatType vatType = QVatType.vatType;

        List<TaxInvoiceLedgerShowDTO> result =  queryFactory
                .select(Projections.constructor(TaxInvoiceLedgerShowDTO.class,
                        client.code.as("clientCode"),
                        client.printClientName.as("clientName"),
                        client.businessRegistrationNumber.as("clientNumber"),
                        voucher.voucherDate.month().as("month"),
                        voucher.id.count().as("voucherCount"),
                        voucher.supplyAmount.sum().as("supplyAmount"),
                        voucher.vatAmount.sum().as("vatAmount")
                ))
                .from(voucher)
                .join(client).on(voucher.client.id.eq(client.id))
                .join(vatType).on(voucher.vatType.id.eq(vatType.id))
                .where(
                        voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                                .and(voucher.electronicTaxInvoiceStatus.eq(ElectronicTaxInvoiceStatus.PUBLISHED))
                                .and(client.code.castToNum(Integer.class).between(Integer.parseInt(dto.getStartClientCode()),
                                        Integer.parseInt(dto.getEndClientCode()))
                                        .and(vatType.transactionType.eq(dto.getTransactionType()))))
                .groupBy(client.code, voucher.voucherDate.month())
                .orderBy(client.code.asc(), voucher.voucherDate.month().asc())
                .fetch();


        Map<Integer, TaxInvoiceLedgerShowDTO> dataMap = result.stream()
                .collect(Collectors.toMap(TaxInvoiceLedgerShowDTO::getMonth, dto2 -> dto2));


        List<TaxInvoiceLedgerShowDTO> endResultData = new ArrayList<>();

        for (int month = dto.getStartDate().getMonthValue(); month <= dto.getStartDate().getMonthValue(); month++) {
            if (dataMap.containsKey(month)) {

                endResultData.add(dataMap.get(month));
            } else {

                endResultData.add(new TaxInvoiceLedgerShowDTO(
                        dataMap.values().stream().findFirst().get().getClientCode(),
                        dataMap.values().stream().findFirst().get().getClientName(),
                        dataMap.values().stream().findFirst().get().getClientNumber(),
                        month,
                        0,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));
            }
        }
        return endResultData;
    }
}
