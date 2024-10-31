package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.QSalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.QSalaryLedgerAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.QSalaryLedgerDate;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SalaryLedgerRepositoryImpl implements SalaryLedgerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SalaryLedgerDTO findLedger(SalaryLedgerSearchDTO dto) {
        QSalaryLedger qSalaryLedger = QSalaryLedger.salaryLedger;

        QSalaryLedgerAllowance qSalaryLedgerAllowance = QSalaryLedgerAllowance.salaryLedgerAllowance;

        List<Tuple> results = queryFactory.select(
                        qSalaryLedger.id,
                        qSalaryLedger.nationalPensionAmount,
                        qSalaryLedger.privateSchoolPensionAmount,
                        qSalaryLedger.healthInsurancePensionAmount,
                        qSalaryLedger.employmentInsuranceAmount,
                        qSalaryLedger.longTermCareInsurancePensionAmount,
                        qSalaryLedger.incomeTaxAmount,
                        qSalaryLedger.localIncomeTaxAmount,
                        qSalaryLedger.totalSalaryAmount,
                        qSalaryLedger.totalDeductionAmount,
                        qSalaryLedger.netPayment,
                        qSalaryLedger.taxableAmount,
                        qSalaryLedger.nonTaxableAmount,
                        qSalaryLedger.taxableIncome,
                        qSalaryLedgerAllowance.name,
                        qSalaryLedgerAllowance.amount
                )
                .from(qSalaryLedger)
                .join(qSalaryLedger.allowance, qSalaryLedgerAllowance) // 수당 리스트 조인
                .where(qSalaryLedger.employee.id.eq(dto.getEmployeeId())
                        .and(qSalaryLedger.salaryLedgerDate.id.eq(dto.getSalaryLedgerDateId())))
                .fetch();

        if (results.isEmpty()) {
            return null;
        }

        // 중복된 SalaryLedger 항목에 대해 수당 리스트를 묶어서 처리
        Map<Long, SalaryLedgerDTO> ledgerMap = new HashMap<>();

        Long ledgerId = 0L;

        for (Tuple row : results) {
            ledgerId = row.get(qSalaryLedger.id);
            SalaryLedgerDTO salaryLedgerDTO = ledgerMap.get(ledgerId);

            if (salaryLedgerDTO == null) {
                salaryLedgerDTO = new SalaryLedgerDTO();
                salaryLedgerDTO.setLedgerId(ledgerId);
                salaryLedgerDTO.setNationalPensionAmount(row.get(qSalaryLedger.nationalPensionAmount));
                salaryLedgerDTO.setPrivateSchoolPensionAmount(row.get(qSalaryLedger.privateSchoolPensionAmount));
                salaryLedgerDTO.setHealthInsurancePensionAmount(row.get(qSalaryLedger.healthInsurancePensionAmount));
                salaryLedgerDTO.setEmploymentInsuranceAmount(row.get(qSalaryLedger.employmentInsuranceAmount));
                salaryLedgerDTO.setLongTermCareInsurancePensionAmount(row.get(qSalaryLedger.longTermCareInsurancePensionAmount));
                salaryLedgerDTO.setIncomeTaxAmount(row.get(qSalaryLedger.incomeTaxAmount));
                salaryLedgerDTO.setLocalIncomeTaxPensionAmount(row.get(qSalaryLedger.localIncomeTaxAmount));
                salaryLedgerDTO.setTotalSalaryAmount(row.get(qSalaryLedger.totalSalaryAmount));
                salaryLedgerDTO.setTotalDeductionAmount(row.get(qSalaryLedger.totalDeductionAmount));
                salaryLedgerDTO.setNetPayment(row.get(qSalaryLedger.netPayment));
                salaryLedgerDTO.setTaxableAmount(row.get(qSalaryLedger.taxableAmount));
                salaryLedgerDTO.setNonTaxableAmount(row.get(qSalaryLedger.nonTaxableAmount));
                salaryLedgerDTO.setTaxableIncome(row.get(qSalaryLedger.taxableIncome));
                salaryLedgerDTO.setAllowances(new ArrayList<>());
                ledgerMap.put(ledgerId, salaryLedgerDTO);
            }

            // 수당 리스트에 항목 추가
            SalaryLedgerAllowanceShowDTO allowanceDTO = new SalaryLedgerAllowanceShowDTO();
            allowanceDTO.setName(row.get(qSalaryLedgerAllowance.name));
            allowanceDTO.setAmount(row.get(qSalaryLedgerAllowance.amount));
            salaryLedgerDTO.getAllowances().add(allowanceDTO);
        }

        return ledgerMap.get(ledgerId);
    }

    @Override
    public List<PaymentStatusManagementShowDTO> showPaymentStatusManagement(PaymentStatusManagementSearchDTO dto) {
        QSalaryLedger qSalaryLedger = QSalaryLedger.salaryLedger;
        QSalaryLedgerAllowance qSalaryLedgerAllowance = QSalaryLedgerAllowance.salaryLedgerAllowance;
        QSalaryLedgerDate qSalaryLedgerDate = QSalaryLedgerDate.salaryLedgerDate;
        QEmployee qEmployee = QEmployee.employee;
        QDepartment qDepartment = QDepartment.department;


        List<Tuple> results = new ArrayList<>();

        switch (dto.getPaymentStatusType()) {
            case EMPLOYEE -> {
                // 사원별
                results = queryFactory.select(
                                        qEmployee.id,
                                        qEmployee.lastName.concat(qEmployee.firstName),
                                        qSalaryLedgerAllowance.name,
                                        qSalaryLedgerAllowance.amount.sum(),
                                        qSalaryLedger.healthInsurancePensionAmount.sum(),
                                        qSalaryLedger.incomeTaxAmount.sum(),
                                        qSalaryLedger.localIncomeTaxAmount.sum(),
                                        qSalaryLedger.longTermCareInsurancePensionAmount.sum(),
                                        qSalaryLedger.nationalPensionAmount.sum(),
                                        qSalaryLedger.netPayment.sum(),
                                        qSalaryLedger.privateSchoolPensionAmount.sum(),
                                        qSalaryLedger.totalDeductionAmount.sum(),
                                        qSalaryLedger.totalSalaryAmount.sum(),
                                        qEmployee.id.count()
                                )
                        .from(qSalaryLedger)
                        .join(qSalaryLedger.allowance, qSalaryLedgerAllowance)
                        .join(qEmployee).on(qEmployee.id.eq(qSalaryLedger.employee.id))
                        .join(qSalaryLedgerDate).on(qSalaryLedger.salaryLedgerDate.id.eq(qSalaryLedgerDate.id))
                        .where(qSalaryLedger.salaryLedgerDate.id.between(dto.getSalaryStartId(), dto.getSalaryEndId()))
                        .groupBy(qEmployee.id, qSalaryLedgerAllowance.name)
                        .fetch();
                break;
            }
            case PERIOD -> {
                // 날짜조건별
                results = queryFactory.select(
                        qSalaryLedger.salaryLedgerDate.id,
                                qSalaryLedgerDate.description,
                                qSalaryLedgerAllowance.name,
                                qSalaryLedgerAllowance.amount.sum(),
                                qSalaryLedger.healthInsurancePensionAmount.sum(),
                                qSalaryLedger.incomeTaxAmount.sum(),
                                qSalaryLedger.localIncomeTaxAmount.sum(),
                                qSalaryLedger.longTermCareInsurancePensionAmount.sum(),
                                qSalaryLedger.nationalPensionAmount.sum(),
                                qSalaryLedger.netPayment.sum(),
                                qSalaryLedger.privateSchoolPensionAmount.sum(),
                                qSalaryLedger.totalDeductionAmount.sum(),
                                qSalaryLedger.totalSalaryAmount.sum(),
                                qEmployee.id.count()
                        )
                        .from(qSalaryLedger)
                        .join(qSalaryLedger.allowance, qSalaryLedgerAllowance)
                        .join(qEmployee).on(qEmployee.id.eq(qSalaryLedger.employee.id))
                        .join(qSalaryLedgerDate).on(qSalaryLedger.salaryLedgerDate.id.eq(qSalaryLedgerDate.id))
                        .where(qSalaryLedger.salaryLedgerDate.id.between(dto.getSalaryStartId(), dto.getSalaryEndId()))
                        .groupBy(qSalaryLedger.salaryLedgerDate.id, qSalaryLedgerAllowance.name)
                        .fetch();
                break;
            }
            case DEPARTMENT -> {

                // 부서별
                results = queryFactory.select(
                                        qDepartment.id,
                                        qDepartment.departmentName,
                                        qSalaryLedgerAllowance.name,
                                        qSalaryLedgerAllowance.amount.sum(),
                                        qSalaryLedger.healthInsurancePensionAmount.sum(),
                                        qSalaryLedger.incomeTaxAmount.sum(),
                                        qSalaryLedger.localIncomeTaxAmount.sum(),
                                        qSalaryLedger.longTermCareInsurancePensionAmount.sum(),
                                        qSalaryLedger.nationalPensionAmount.sum(),
                                        qSalaryLedger.netPayment.sum(),
                                        qSalaryLedger.privateSchoolPensionAmount.sum(),
                                        qSalaryLedger.totalDeductionAmount.sum(),
                                        qSalaryLedger.totalSalaryAmount.sum(),
                                        qDepartment.id.count().divide(dto.getSalaryEndId())
                                )
                        .from(qSalaryLedger)
                        .join(qSalaryLedger.allowance, qSalaryLedgerAllowance)
                        .join(qEmployee).on(qEmployee.id.eq(qSalaryLedger.employee.id))
                        .join(qDepartment).on(qDepartment.id.eq(qEmployee.department.id))
                        .where(qSalaryLedger.salaryLedgerDate.id.between(dto.getSalaryStartId(), dto.getSalaryEndId()))
                        .groupBy(qDepartment.id, qSalaryLedgerAllowance.name)
                        .fetch();
                break;
            }

        }

        // 중복된 SalaryLedger 항목에 대해 수당 리스트를 묶어서 처리
        Map<Long, PaymentStatusManagementShowDTO> ledgerMap = new HashMap<>();

        Long ledgerId = 0L;
        String name = "";
        for (Tuple row : results) {
            PaymentStatusManagementShowDTO result = ledgerMap.get(ledgerId);

            switch (dto.getPaymentStatusType()) {
                case DEPARTMENT -> {
                    ledgerId = row.get(qDepartment.id);
                    name = row.get(qDepartment.departmentName);
                    break;
                }
                case PERIOD -> {
                    ledgerId = row.get(qSalaryLedgerDate.id);
                    name = row.get(qSalaryLedgerDate.description);
                    break;
                }
                case EMPLOYEE -> {
                    ledgerId = row.get(qEmployee.id);
                    name = row.get(qEmployee.lastName.concat(qEmployee.firstName));
                    break;
                }
            }

            if (result == null) {
                PaymentStatusManagementShowDTO newDto = new PaymentStatusManagementShowDTO();
                newDto.setId(ledgerId);
                newDto.setDescriptionName(name);
                newDto.setNationalPensionAmount(row.get(qSalaryLedger.nationalPensionAmount));
                newDto.setPrivateSchoolPensionAmount(row.get(qSalaryLedger.privateSchoolPensionAmount));
                newDto.setHealthInsurancePensionAmount(row.get(qSalaryLedger.healthInsurancePensionAmount));
                newDto.setEmploymentInsuranceAmount(row.get(qSalaryLedger.employmentInsuranceAmount));
                newDto.setLongTermCareInsurancePensionAmount(row.get(qSalaryLedger.longTermCareInsurancePensionAmount));
                newDto.setIncomeTaxAmount(row.get(qSalaryLedger.incomeTaxAmount));
                newDto.setLocalIncomeTaxPensionAmount(row.get(qSalaryLedger.localIncomeTaxAmount));
                newDto.setTotalSalaryAmount(row.get(qSalaryLedger.totalSalaryAmount));
                newDto.setTotalDeductionAmount(row.get(qSalaryLedger.totalDeductionAmount));
                newDto.setNetPayment(row.get(qSalaryLedger.netPayment));
                newDto.setTaxableIncome(row.get(qSalaryLedger.taxableIncome));
                newDto.setAllowances(new ArrayList<>());
                ledgerMap.put(ledgerId, newDto);
            }

            // 수당 리스트에 항목 추가
            SalaryLedgerAllowanceShowDTO allowanceDTO = new SalaryLedgerAllowanceShowDTO();
            allowanceDTO.setName(row.get(qSalaryLedgerAllowance.name));
            allowanceDTO.setAmount(row.get(qSalaryLedgerAllowance.amount.sum()));
            result.getAllowances().add(allowanceDTO);
        }


        return ledgerMap.values().stream().toList();
    }
}
