package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.resolvedSaleAndPurchaseVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.SalesAndPurChaseLedgerShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QJournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.QVatType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
