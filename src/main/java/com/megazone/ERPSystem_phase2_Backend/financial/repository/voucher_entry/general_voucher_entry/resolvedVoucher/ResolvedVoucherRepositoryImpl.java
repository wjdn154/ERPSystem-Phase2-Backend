package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QDepartmentEmployee;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResolvedVoucherRepositoryImpl implements ResolvedVoucherRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> deleteVoucherByManager(ResolvedVoucherDeleteDTO dto) {
        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;

        List<Long> deletedVoucher = dto.getSearchVoucherNumList().stream()
                .flatMap(voucherNum -> queryFactory.select(qResolvedVoucher.id)
                        .from(qResolvedVoucher)
                        .where(qResolvedVoucher.voucherDate.eq(dto.getSearchDate())
                                        .and(qResolvedVoucher.voucherNumber.eq(voucherNum))
//                                .and(qUnresolvedVoucher.voucherManager.id.eq(managerId)))
                        ).fetch().stream())
                .collect(Collectors.toList());

        if(!deletedVoucher.isEmpty()) {
            queryFactory.delete(qResolvedVoucher)
                    .where(qResolvedVoucher.id.in(deletedVoucher))
                    .execute();
            return deletedVoucher;
        }
        return null;
    }

    @Override
    public List<GeneralShowDTO> generalSelectShow(GeneralSelectDTO dto) {
        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;
        return queryFactory
                .select(
                        qResolvedVoucher.voucherDate.month(),
                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class)
                )
                .from(qResolvedVoucher)
                .where(qResolvedVoucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(qResolvedVoucher.accountSubject.code.eq(dto.getAccountCode())))
                .groupBy(qResolvedVoucher.voucherDate.month())
                .orderBy(qResolvedVoucher.voucherDate.month().asc())
                .fetch()
                .stream()
                .map(tuple -> GeneralShowDTO.create(
                        tuple.get(qResolvedVoucher.voucherDate.month()), // 월 숫자로 직접 사용
                        tuple.get(qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class)),
                        tuple.get(qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class)),
                        BigDecimal.ZERO // 필요에 따라 조정
                ))
                .toList();
    }

    @Override
    public List<GeneralAccountListDTO> generalList(GeneralDTO dto) {

        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;

        return queryFactory
                .selectDistinct(
                        qResolvedVoucher.accountSubject.id,
                        qResolvedVoucher.accountSubject.code,
                        qResolvedVoucher.accountSubject.name
                )
                .from(qResolvedVoucher)
                .where(qResolvedVoucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(qResolvedVoucher.accountSubject.code.between(dto.getStartSubjectCode(), dto.getEndSubjectCode())))
                .orderBy(qResolvedVoucher.accountSubject.code.asc())
                .fetch()
                .stream()
                .map(tuple -> GeneralAccountListDTO.create(
                        tuple.get(qResolvedVoucher.accountSubject.id),
                        tuple.get(qResolvedVoucher.accountSubject.code),
                        tuple.get(qResolvedVoucher.accountSubject.name)
                ))
                .toList();
    }

    @Override
    public List<ClientLedgerShowDTO> clientLedgerList(ClientLedgerSearchDTO dto) {

        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QClient client = QClient.client;
        QEmployee employee = QEmployee.employee;
        QDepartment department = QDepartment.department;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;

        return queryFactory
                .select(Projections.constructor(ClientLedgerShowDTO.class,
                        client.code,
                        client.printClientName,
                        client.businessRegistrationNumber,
                        client.representativeName,
                        Expressions.constant(BigDecimal.ZERO), // previousCash
                        voucher.debitAmount.sum(), // debitTotalAmount
                        voucher.creditAmount.sum(), // creditTotalAmount
                        voucher.debitAmount.sum().subtract(voucher.creditAmount.sum()), // cashTotalAmount
                        department.departmentName, // managerDepartment
                        employee.lastName.concat(employee.firstName) // managerName
                ))
                .from(voucher)
                .join(voucher.client, client)
                .join(voucher.client.employee, employee)
                .join(employee.department, department)
                .join(voucher.accountSubject, accountSubject)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(client.code.castToNum(Integer.class).between(Integer.valueOf(dto.getClientStartCode()), Integer.valueOf(dto.getClientEndCode())))
                        .and(accountSubject.code.eq(dto.getAccountCode())))
                .groupBy(client.code, client.printClientName, client.businessRegistrationNumber, client.representativeName, /*department.departmentName,*/ employee.firstName, employee.lastName)
                .orderBy(client.code.asc())
                .fetch();
    }

    @Override
    public List<ClientListDTO> clientAndAccountSubjectLedgerList(ClientAndAccountSubjectLedgerSearchDTO dto) {

        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QClient client = QClient.client;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;

        return queryFactory
                .select(Projections.constructor(ClientListDTO.class,
                        client.id,
                        client.code,
                        client.printClientName,
                        client.businessRegistrationNumber,
                        client.representativeName
                ))
                .from(voucher)
                .join(voucher.client, client)
                .join(voucher.accountSubject, accountSubject)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(client.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartClientCode()), Integer.valueOf(dto.getEndClientCode())))
                        .and(accountSubject.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartAccountSubjectCode()), Integer.valueOf(dto.getEndAccountSubjectCode()))))
                .groupBy(client.code, client.printClientName, client.businessRegistrationNumber, client.representativeName)
                .orderBy(client.code.asc())
                .fetch();

    }

    @Override
    public List<ClientAndAccountSubjectLedgerShowDetailDTO> clientAndAccountSubjectLedgerDetail(ClientAndAccountSubjectLedgerShowDetailConditionDTO dto) {
        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QClient client = QClient.client;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;

        return queryFactory
                .select(Projections.constructor(ClientAndAccountSubjectLedgerShowDetailDTO.class,
                        accountSubject.code,
                        accountSubject.name,
                        Expressions.constant(BigDecimal.ZERO),
                        voucher.debitAmount.sum(),
                        voucher.creditAmount.sum(),
                        voucher.debitAmount.sum().subtract(voucher.creditAmount.sum())
                ))
                .from(voucher)
                .join(voucher.client, client)
                .join(voucher.accountSubject, accountSubject)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(client.id.eq(dto.getClientId()))
                        .and(accountSubject.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartAccountSubjectCode()), Integer.valueOf(dto.getEndAccountSubjectCode()))))
                .groupBy(accountSubject.code,accountSubject.name)
                .orderBy(accountSubject.code.asc())
                .fetch();
    }

    @Override
    public List<CashJournalShowDTO> cashJournalShow(CashJournalSearchDTO dto) {
        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QClient client = QClient.client;

        return queryFactory.select(
                        Projections.constructor(CashJournalShowDTO.class,
                                voucher.id,
                                voucher.voucherDate,
                                voucher.transactionDescription,
                                client.code,
                                client.printClientName,
                                // Case문
                                new CaseBuilder()
                                        .when(voucher.voucherType.eq(VoucherType.DEPOSIT))
                                        .then(voucher.creditAmount)
                                        .otherwise(BigDecimal.ZERO),
                                new CaseBuilder()
                                        .when(voucher.voucherType.eq(VoucherType.WITHDRAWAL))
                                        .then(voucher.debitAmount)
                                        .otherwise(BigDecimal.ZERO),
                                Expressions.constant(BigDecimal.ZERO)
                        ))
                .from(voucher)
                .join(voucher.client, client)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(voucher.voucherType.eq(VoucherType.DEPOSIT)
                                .or(voucher.voucherType.eq(VoucherType.WITHDRAWAL)))
                        .and(voucher.accountSubject.code.ne("101")))
                .orderBy(voucher.voucherDate.asc())
                .fetch();
    }

    @Override
    public List<AccountLedgerAccListDTO> accountLedgerList(AccountLedgerSearchDTO dto) {
        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;

        return queryFactory.select(
                        Projections.constructor(AccountLedgerAccListDTO.class,
                                accountSubject.id,
                                accountSubject.code,
                                accountSubject.name
                        ))
                .from(voucher)
                .join(voucher.accountSubject, accountSubject)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(voucher.accountSubject.code.between(dto.getStartAccountCode(), dto.getEndAccountCode())))
                .groupBy(accountSubject.code,accountSubject.name)
                .orderBy(voucher.accountSubject.code.asc())
                .fetch();
    }

    @Override
    public List<AccountLedgerShowDTO> accountLedgerDetail(AccountLedgerDetailEntryDTO dto) {
        QResolvedVoucher voucher = QResolvedVoucher.resolvedVoucher;
        QClient client = QClient.client;
        QEmployee employee = QEmployee.employee;
        QAccountSubject accountSubject = QAccountSubject.accountSubject;
        QDepartment department = QDepartment.department;

        return queryFactory.select(
                        Projections.constructor(AccountLedgerShowDTO.class,
                                    voucher.id,
                                    voucher.voucherDate,
                                    voucher.transactionDescription,
                                    client.code,
                                    client.printClientName,
                                    voucher.debitAmount,
                                    voucher.creditAmount,
                                    voucher.debitAmount.subtract(voucher.creditAmount),
                                    voucher.voucherNumber,
                                    voucher.voucherApprovalTime,
                                    employee.department.departmentName,
                                    employee.lastName.concat(employee.firstName)
                        ))
                .from(voucher)
                .join(voucher.accountSubject, accountSubject)
                .join(voucher.voucherManager, employee)
                .join(employee.department, department)
                .join(voucher.client, client)
                .where(voucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(voucher.accountSubject.id.eq(dto.getAccountId())))
                .orderBy(voucher.voucherDate.asc())
                .fetch();
    }


}
