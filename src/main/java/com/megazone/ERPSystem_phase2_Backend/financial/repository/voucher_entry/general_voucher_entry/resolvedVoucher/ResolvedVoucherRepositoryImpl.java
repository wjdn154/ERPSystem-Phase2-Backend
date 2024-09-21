package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QDepartmentEmployee;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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
//        QClient qClient = QClient.client;
//        QEmployee qEmployee = new QEmployee("vm"); // 별칭 사용
//        QDepartmentEmployee qDepartmentEmployee = new QDepartmentEmployee("d"); // 별칭 사용
//        QAccountSubject qAccountSubject = QAccountSubject.accountSubject;
//
//        NumberExpression<Integer> clientCodeAsInteger = Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", qClient.code);
//
//        NumberExpression<BigDecimal> totalDebitAmount = qResolvedVoucher.debitAmount.sum();
//        NumberExpression<BigDecimal> totalCreditAmount = qResolvedVoucher.creditAmount.sum();
//
//        List<Tuple> fetchtest = queryFactory
//                .select(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
//                        totalDebitAmount,
//                        totalCreditAmount,
////                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .from(qResolvedVoucher)
//                .innerJoin(qClient).on(qResolvedVoucher.client.id.eq(qClient.id))
//                .innerJoin(qEmployee).on(qResolvedVoucher.voucherManager.id.eq(qEmployee.id))
////                .innerJoin(qDepartmentEmployee).on(qEmployee.department.id.eq(qDepartmentEmployee.id))
//                .join(qAccountSubject).on(qResolvedVoucher.accountSubject.id.eq(qAccountSubject.id))
//                .where(
//                        qResolvedVoucher.voucherDate.between(
//                                LocalDate.parse("2024-01-01"),
//                                LocalDate.parse("2024-12-31")
//                        ),
//                        clientCodeAsInteger.between(1, 10),
//                        qAccountSubject.code.eq("108")
//                )
//                .groupBy(
//                        qClient.code,
//                        qClient.printClientName,
//                        qClient.businessRegistrationNumber,
//                        qClient.representativeName,
////                        qDepartmentEmployee.departmentName,
//                        qEmployee.firstName,
//                        qEmployee.lastName
//                )
//                .orderBy(qClient.code.asc())
//                .fetch();
//
//        return fetchtest.stream()
//                .map(tuple -> new ClientLedgerShowDTO(
//                        tuple.get(qClient.code),
//                        tuple.get(qClient.printClientName),
//                        tuple.get(qClient.businessRegistrationNumber),
//                        tuple.get(qClient.representativeName),
//                        BigDecimal.ZERO, // 전기이월 (추가 로직 필요)
//                        tuple.get(totalDebitAmount),
//                        tuple.get(totalCreditAmount),
//                        BigDecimal.ZERO, // 잔액 (추가 로직 필요)
////                        "만수이",
//                        "만수이",
////                        tuple.get(qDepartmentEmployee.departmentName),
//                        tuple.get(qEmployee.firstName) + " " + tuple.get(qEmployee.lastName)
//                ))
//                .toList();

        QResolvedVoucher rv = QResolvedVoucher.resolvedVoucher;
        QClient c = QClient.client;
        QEmployee vm = QEmployee.employee;
        QDepartment d = QDepartment.department;
        QAccountSubject a = QAccountSubject.accountSubject;

        return queryFactory
                .select(Projections.constructor(ClientLedgerShowDTO.class,
                        c.code,
                        c.printClientName,
                        c.businessRegistrationNumber,
                        c.representativeName,
                        Expressions.constant(BigDecimal.ZERO), // previousCash
                        rv.debitAmount.sum(), // debitTotalAmount
                        rv.creditAmount.sum(), // creditTotalAmount
                        rv.debitAmount.sum().subtract(rv.creditAmount.sum()), // cashTotalAmount
                        d.departmentName, // managerDepartment
                        vm.firstName.concat(" ").concat(vm.lastName) // managerName
                ))
                .from(rv)
                .join(rv.client, c)
                .join(rv.voucherManager, vm)
                .join(vm.department, d)
                .join(rv.accountSubject, a)
                .where(rv.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(c.code.castToNum(Integer.class).between(Integer.valueOf(dto.getClientStartCode()), Integer.valueOf(dto.getClientEndCode())))
                        .and(a.code.eq(dto.getAccountCode())))
                .groupBy(c.code, c.printClientName, c.businessRegistrationNumber, c.representativeName, d.departmentName, vm.firstName, vm.lastName)
                .orderBy(c.code.asc())
                .fetch();
    }

    @Override
    public List<ClientListDTO> clientAndAccountSubjectLedgerList(ClientAndAccountSubjectLedgerSearchDTO dto) {

        QResolvedVoucher rv = QResolvedVoucher.resolvedVoucher;
        QClient c = QClient.client;
        QAccountSubject a = QAccountSubject.accountSubject;

        return queryFactory
                .select(Projections.constructor(ClientListDTO.class,
                        c.code,
                        c.printClientName,
                        c.businessRegistrationNumber,
                        c.representativeName
                ))
                .from(rv)
                .join(rv.client, c)
                .join(rv.accountSubject, a)
                .where(rv.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(c.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartClientCode()), Integer.valueOf(dto.getEndClientCode())))
                        .and(a.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartAccountSubjectCode()), Integer.valueOf(dto.getEndAccountSubjectCode()))))
                .groupBy(c.code, c.printClientName, c.businessRegistrationNumber, c.representativeName)
                .orderBy(c.code.asc())
                .fetch();

    }

    @Override
    public List<ClientAndAccountSubjectLedgerShowDetailDTO> clientAndAccountSubjectLedgerDetail(ClientAndAccountSubjectLedgerShowDetailConditionDTO dto) {
        QResolvedVoucher rv = QResolvedVoucher.resolvedVoucher;
        QClient c = QClient.client;
        QAccountSubject a = QAccountSubject.accountSubject;

        System.out.println("Client ID: " + dto.getClientId());
        System.out.println("Start Account Subject Code: " + dto.getStartAccountSubjectCode());
        return queryFactory
                .select(Projections.constructor(ClientAndAccountSubjectLedgerShowDetailDTO.class,
                        a.code,
                        a.name,
                        Expressions.constant(BigDecimal.ZERO),
                        rv.debitAmount.sum(),
                        rv.creditAmount.sum(),
                        rv.debitAmount.sum().subtract(rv.creditAmount.sum())
                ))
                .from(rv)
                .join(rv.client, c)
                .join(rv.accountSubject, a)
                .where(rv.voucherDate.between(dto.getStartDate(), dto.getEndDate())
                        .and(c.id.eq(dto.getClientId()))
                        .and(a.code.castToNum(Integer.class).between(Integer.valueOf(dto.getStartAccountSubjectCode()), Integer.valueOf(dto.getEndAccountSubjectCode()))))
                .groupBy(a.code,a.name)
                .orderBy(a.code.asc())
                .fetch();
    }


}
