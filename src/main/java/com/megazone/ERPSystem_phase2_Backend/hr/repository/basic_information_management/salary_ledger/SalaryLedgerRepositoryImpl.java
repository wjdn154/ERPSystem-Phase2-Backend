package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary_ledger;

import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.QSalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.QSalaryLedgerAllowance;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.SalaryLedger;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerSearchDTO;
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
                        qSalaryLedgerAllowance.name,
                        qSalaryLedgerAllowance.amount
                )
                .from(qSalaryLedger)
                .leftJoin(qSalaryLedger.allowance, qSalaryLedgerAllowance) // 수당 리스트 조인
                .where(qSalaryLedger.salaryLedgerDate.id.eq(dto.getSalaryLedgerDateId())
                        .and(qSalaryLedger.employee.id.eq(dto.getEmployeeId())))
                .fetch();

        if(results.isEmpty()) {
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
                salaryLedgerDTO.setAllowances(new ArrayList<>());
                ledgerMap.put(ledgerId, salaryLedgerDTO);
            }

            // 수당 리스트에 항목 추가
            SalaryLedgerAllowanceDTO allowanceDTO = new SalaryLedgerAllowanceDTO();
            allowanceDTO.setName(row.get(qSalaryLedgerAllowance.name));
            allowanceDTO.setAmount(row.get(qSalaryLedgerAllowance.amount));
            salaryLedgerDTO.getAllowances().add(allowanceDTO);
        }

        return ledgerMap.get(ledgerId); // 중복 없이 SalaryLedgerDTO 리스트 반환
    }
}
