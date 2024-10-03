package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.TaxInvoiceLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QJournalEntry;
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
import java.util.List;
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
        QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;

        return queryFactory.select(
                        Projections.constructor(SalesAndPurChaseLedgerShowDTO.class,
                                voucher.id,
                                vatType.vatName,
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
                .join(client)
                .join(vatType).on(vatType.id.eq(voucher.vatType.id))
                .join(journalEntry).on(journalEntry.id.eq(voucher.journalEntry.id))
                .join(employee).on(employee.id.eq(voucher.voucherManager.id))
                .join(department).on(employee.department.id.eq(department.id))
                .where(voucher.voucherDate.between(dto.getStartDate(),dto.getEndDate()))
                .orderBy(voucher.voucherDate.asc())
                .fetch();
    }

    @Override
    public Object showTaxInvoiceLedger(TaxInvoiceLedgerSearchDTO dto) {
//        QClient client = QClient.client;
//        QResolvedSaleAndPurchaseVoucher voucher = QResolvedSaleAndPurchaseVoucher.resolvedSaleAndPurchaseVoucher;
//
//
//        List<Integer> monthsList = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
//
//        return queryFactory.select(
//                        client.code.as("clientCode"),
//                        client.printClientName.as("clientName"),
//                        client.businessRegistrationNumber.as("clientNumber"),
//                        voucher.voucherDate.month().as("month"),  // voucher의 월 정보 사용
//                        voucher.id.count().coalesce(0L).as("voucherCount"),
//                        voucher.supplyAmount.sum().coalesce(0L).as("supplyAmount"),
//                        voucher.vatAmount.sum().coalesce(0L).as("vatAmount")
//                )
//                .from(client)
//                .leftJoin(voucher)
//                .on(voucher.voucherDate.month().in(monthsList)  // 지정된 월 리스트로 필터링
//                        .and(voucher.voucherDate.between(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 8, 31)))
//                        .and(voucher.client.id.eq(client.id))
//                        .and(voucher.electronicTaxInvoiceStatus.eq(ElectronicTaxInvoiceStatus.PUBLISHED)))
//                .where(client.code.between("1", "30"))
//                .groupBy(client.code, client.printClientName, client.businessRegistrationNumber, voucher.voucherDate.month())
//                .orderBy(client.code.asc(), voucher.voucherDate.month().asc())
//                .fetch();
        return null;
    }
}
