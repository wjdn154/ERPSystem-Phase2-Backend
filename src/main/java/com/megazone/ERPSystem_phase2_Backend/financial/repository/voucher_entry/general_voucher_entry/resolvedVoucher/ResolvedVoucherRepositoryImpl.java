package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QDepartmentEmployee;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
//        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;
//
//        return queryFactory
//                .select(
//                        qResolvedVoucher.client.code,
//                        qResolvedVoucher.client.printClientName,
//                        qResolvedVoucher.client.businessRegistrationNumber,
//                        qResolvedVoucher.client.representativeName,
//                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
//                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class),
//                        qResolvedVoucher.voucherManager.department.departmentName,
//                        qResolvedVoucher.voucherManager.firstName,
//                        qResolvedVoucher.voucherManager.lastName
//                )
//                .from(qResolvedVoucher)
//                .where(qResolvedVoucher.voucherDate.between(dto.getStartDate(), dto.getEndDate()) // 날짜 범위 조건 추가
//                        .and(qResolvedVoucher.client.code.between(dto.getClientStartCode(), dto.getClientEndCode()))
//                        .and(qResolvedVoucher.accountSubject.code.eq(dto.getAccountCode())))
//                .groupBy(qResolvedVoucher.client.code, qResolvedVoucher.client.printClientName, qResolvedVoucher.client.businessRegistrationNumber,
//                        qResolvedVoucher.client.representativeName, qResolvedVoucher.voucherManager.department.departmentName,
//                        qResolvedVoucher.voucherManager.firstName,qResolvedVoucher.voucherManager.lastName)
//                .orderBy(qResolvedVoucher.client.code.asc())
//                .fetch()
//                .stream()
//                .map(tuple -> ClientLedgerShowDTO.create(
//                        tuple.get(qResolvedVoucher.client.code),
//                        tuple.get(qResolvedVoucher.client.printClientName),
//                        tuple.get(qResolvedVoucher.client.businessRegistrationNumber),
//                        tuple.get(qResolvedVoucher.client.representativeName),
//                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
//                        tuple.get(qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class)),
//                        tuple.get(qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class)),
//                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
//                        tuple.get(qResolvedVoucher.voucherManager.department.departmentName),
//                        tuple.get(qResolvedVoucher.voucherManager.firstName.concat(qResolvedVoucher.voucherManager.lastName))
//                ))
//                .toList();
//

        QResolvedVoucher qResolvedVoucher = QResolvedVoucher.resolvedVoucher;
        QClient qClient = QClient.client;
        QEmployee qEmployee = QEmployee.employee;
        QDepartmentEmployee qDepartmentEmployee = QDepartmentEmployee.departmentEmployee;
        QAccountSubject qAccountSubject = QAccountSubject.accountSubject;
//
//        return queryFactory
//                .select(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
//                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class),
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .from(qResolvedVoucher)
//                .join(qClient).on(qResolvedVoucher.client.id.eq(qClient.id))
//                .join(qEmployee).on(qResolvedVoucher.voucherManager.id.eq(qEmployee.id))
//                .join(qDepartmentEmployee).on(qEmployee.department.id.eq(qDepartmentEmployee.id))
//                .join(qAccountSubject).on(qResolvedVoucher.accountSubject.id.eq(qAccountSubject.id))
//                .where(qResolvedVoucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
//                        .and(qClient.code.between(dto.getClientStartCode(), dto.getClientEndCode()))
//                        .and(qAccountSubject.code.eq(dto.getAccountCode()))
//                )
//                .groupBy(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .orderBy(qClient.code.asc())
//                .fetch()
//                .stream()
//                .map(tuple -> new ClientLedgerShowDTO(
//                        tuple.get(qClient.code),
//                        tuple.get(qClient.printClientName),
//                        tuple.get(qClient.businessRegistrationNumber),
//                        tuple.get(qClient.representativeName),
//                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
//                        tuple.get(qResolvedVoucher.debitAmount.sum()),
//                        tuple.get(qResolvedVoucher.creditAmount.sum()),
//                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
//                        tuple.get(qDepartmentEmployee.departmentName),
//                        tuple.get(qEmployee.firstName) + " " + tuple.get(qEmployee.lastName)
//                ))
//                .toList();

//        return queryFactory
//                .select(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
//                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class),
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .from(qResolvedVoucher)
//                .join(qClient).on(qResolvedVoucher.client.id.eq(qClient.id))
//                .join(qEmployee).on(qResolvedVoucher.voucherManager.id.eq(qEmployee.id))
//                .join(qDepartmentEmployee).on(qEmployee.department.id.eq(qDepartmentEmployee.id))
//                .join(qAccountSubject).on(qResolvedVoucher.accountSubject.id.eq(qAccountSubject.id))
//                .where(qResolvedVoucher.voucherDate.between(dto.getStartDate(), dto.getEndDate())
//                        .and(Expressions.numberTemplate(Integer.class, "CAST({0} AS SIGNED)", qClient.code)
//                                .between(Integer.parseInt(dto.getClientStartCode()), Integer.parseInt(dto.getClientEndCode())))
//                        .and(qAccountSubject.code.eq(dto.getAccountCode()))
//                )
//                .groupBy(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .orderBy(Expressions.numberTemplate(Integer.class, "CAST({0} AS SIGNED)", qClient.code).asc())
//                .fetch()
//                .stream()
//                .map(tuple -> new ClientLedgerShowDTO(
//                        tuple.get(qClient.code),
//                        tuple.get(qClient.printClientName),
//                        tuple.get(qClient.businessRegistrationNumber),
//                        tuple.get(qClient.representativeName),
//                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
//                        tuple.get(qResolvedVoucher.debitAmount.sum()),
//                        tuple.get(qResolvedVoucher.creditAmount.sum()),
//                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
//                        tuple.get(qDepartmentEmployee.departmentName),
//                        tuple.get(qEmployee.firstName) + " " + tuple.get(qEmployee.lastName)
//                ))
//                .toList();


//        return queryFactory
//                .select(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qResolvedVoucher.debitAmount.sum().castToNum(BigDecimal.class),
//                        qResolvedVoucher.creditAmount.sum().castToNum(BigDecimal.class),
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .from(qResolvedVoucher)
//                .join(qClient).on(qResolvedVoucher.client.id.eq(qClient.id))
//                .join(qEmployee).on(qResolvedVoucher.voucherManager.id.eq(qEmployee.id))
//                .join(qDepartmentEmployee).on(qEmployee.department.id.eq(qDepartmentEmployee.id))
//                .join(qAccountSubject).on(qResolvedVoucher.accountSubject.id.eq(qAccountSubject.id))
//                        .where(Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", qClient.code)
//                                .between(Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", dto.getClientStartCode()),
//                                                Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", dto.getClientEndCode()))
//                        .and(qAccountSubject.code.eq(dto.getAccountCode())))
//                .groupBy(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .orderBy(qClient.code.asc())
//                .fetch()
//                .stream()
//                .map(tuple -> new ClientLedgerShowDTO(
//                        tuple.get(qClient.code),
//                        tuple.get(qClient.printClientName),
//                        tuple.get(qClient.businessRegistrationNumber),
//                        tuple.get(qClient.representativeName),
//                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
//                        tuple.get(qResolvedVoucher.debitAmount.sum()),
//                        tuple.get(qResolvedVoucher.creditAmount.sum()),
//                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
//                        tuple.get(qDepartmentEmployee.departmentName),
//                        tuple.get(qEmployee.firstName) + " " + tuple.get(qEmployee.lastName)
//                ))
//                .toList();

        List<Tuple> fetchtest = queryFactory
                .select(
                        qClient.code.as("clientCode"),
                        qClient.printClientName.as("printClientName"),
                        qClient.businessRegistrationNumber.as("businessRegistrationNumber"),
                        qClient.representativeName.as("representativeName"),
                        qResolvedVoucher.debitAmount.sum().as("totalDebitAmount"),
                        qResolvedVoucher.creditAmount.sum().as("totalCreditAmount"),
                        qDepartmentEmployee.departmentName.as("departmentName"),
                        qEmployee.firstName.as("firstName"),
                        qEmployee.lastName.as("lastName")
                )
                .from(qResolvedVoucher)
                .join(qClient).on(qResolvedVoucher.client.id.eq(qClient.id))
                .join(qEmployee).on(qResolvedVoucher.voucherManager.id.eq(qEmployee.id))
                .join(qDepartmentEmployee).on(qEmployee.department.id.eq(qDepartmentEmployee.id))
                .join(qAccountSubject).on(qResolvedVoucher.accountSubject.id.eq(qAccountSubject.id))
                .where(
                        qResolvedVoucher.voucherDate.between(
                                LocalDate.parse("2024-01-01"),
                                LocalDate.parse("2024-12-31")
                        ),
                        Expressions.numberTemplate(Integer.class, "CAST({0} AS UNSIGNED)", qClient.code)
                                .between(1, 10),
                        qAccountSubject.code.eq("108")
                )
                .groupBy(
                        qClient.code,
                        qClient.printClientName,
                        qClient.businessRegistrationNumber,
                        qClient.representativeName,
                        qDepartmentEmployee.departmentName,
                        qEmployee.firstName,
                        qEmployee.lastName
                )
                .orderBy(qClient.code.asc())
                .fetch();

        return fetchtest
                        .stream()
                .map(tuple -> new ClientLedgerShowDTO(
                        tuple.get(qClient.code),
                        tuple.get(qClient.printClientName),
                        tuple.get(qClient.businessRegistrationNumber),
                        tuple.get(qClient.representativeName),
                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
                        tuple.get(qResolvedVoucher.debitAmount.sum()),
                        tuple.get(qResolvedVoucher.creditAmount.sum()),
                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
                        tuple.get(qDepartmentEmployee.departmentName),
                        tuple.get(qEmployee.firstName) + " " + tuple.get(qEmployee.lastName)
                ))
                .toList();
    }



}
