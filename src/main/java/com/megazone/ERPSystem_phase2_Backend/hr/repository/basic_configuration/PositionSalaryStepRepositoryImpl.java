package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDateDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.salary_ledger.dto.SalaryLedgerAllowanceShowDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PositionSalaryStepRepositoryImpl implements PositionSalaryStepRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PositionSalaryStepDTO> show(Long positionId) {
        QPositionSalaryStep qPositionSalaryStep = QPositionSalaryStep.positionSalaryStep;
        QPositionSalaryStepAllowance qPositionSalaryStepAllowance = QPositionSalaryStepAllowance.positionSalaryStepAllowance;
        QSalaryStep qSalaryStep = QSalaryStep.salaryStep;
        QAllowance qAllowance = QAllowance.allowance;

        return queryFactory.select(
                        Projections.constructor(
                                PositionSalaryStepDTO.class,  // DTO로 결과 매핑
                                qPositionSalaryStep.id,
                                qSalaryStep.id,
                                qSalaryStep.name,
                                qAllowance.id,
                                qAllowance.name,
                                qPositionSalaryStepAllowance.amount,
                                qPositionSalaryStep.useStartDate,
                                qPositionSalaryStep.useEndDate
                        )
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(qPositionSalaryStep.positions.id.eq(positionId)
                        .and(qPositionSalaryStep.useEndDate.isNull()))
                .fetch();
    }

    @Override
    public List<PositionSalaryStepDTO> endDateShow(PositionSalaryStepSearchDTO dto) {
        QPositionSalaryStep qPositionSalaryStep = QPositionSalaryStep.positionSalaryStep;
        QPositionSalaryStepAllowance qPositionSalaryStepAllowance = QPositionSalaryStepAllowance.positionSalaryStepAllowance;
        QSalaryStep qSalaryStep = QSalaryStep.salaryStep;
        QAllowance qAllowance = QAllowance.allowance;

        BooleanBuilder whereCondition = new BooleanBuilder();

        whereCondition.and(qPositionSalaryStep.positions.id.eq(dto.getPositionId()));

        if(dto.getEndMonth() == null) {
            whereCondition.and(qPositionSalaryStep.useEndDate.isNull());
        }
        else {
            whereCondition.and(qPositionSalaryStep.useEndDate.eq(dto.getEndMonth()));
        }

        return queryFactory.select(
                        Projections.constructor(
                                PositionSalaryStepDTO.class,  // DTO로 결과 매핑
                                qPositionSalaryStep.id,
                                qSalaryStep.id,
                                qSalaryStep.name,
                                qAllowance.id,
                                qAllowance.name,
                                qPositionSalaryStepAllowance.amount,
                                qPositionSalaryStep.useStartDate,
                                qPositionSalaryStep.useEndDate
                        )
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(whereCondition)
                .fetch();
    }

    @Override
    public List<PositionSalaryStepDateDetailDTO> dateList(Long positionId) {
        QPositionSalaryStep qPositionSalaryStep = QPositionSalaryStep.positionSalaryStep;
        QPositionSalaryStepAllowance qPositionSalaryStepAllowance = QPositionSalaryStepAllowance.positionSalaryStepAllowance;
        QSalaryStep qSalaryStep = QSalaryStep.salaryStep;
        QAllowance qAllowance = QAllowance.allowance;

        return queryFactory.select(
                        Projections.constructor(
                                PositionSalaryStepDateDetailDTO.class,  // DTO로 결과 매핑
                                qPositionSalaryStep.useStartDate,
                                qPositionSalaryStep.useEndDate
                        )
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(qPositionSalaryStep.positions.id.eq(positionId))
                .groupBy(qPositionSalaryStep.useStartDate,qPositionSalaryStep.useEndDate)
                .fetch();
    }

    @Override
    public BigDecimal getSalaryAmount(Long positionId, Long SalaryStepId) {
        QPositionSalaryStep qPositionSalaryStep = QPositionSalaryStep.positionSalaryStep;
        QPositionSalaryStepAllowance qPositionSalaryStepAllowance = QPositionSalaryStepAllowance.positionSalaryStepAllowance;
        QSalaryStep qSalaryStep = QSalaryStep.salaryStep;
        QAllowance qAllowance = QAllowance.allowance;

        return queryFactory.select(
                        qPositionSalaryStepAllowance.amount.sum().castToNum(BigDecimal.class)
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(qPositionSalaryStep.positions.id.eq(positionId)
                        .and(qSalaryStep.id.eq(SalaryStepId)))
                .fetchOne();
    }

    @Override
    public List<SalaryLedgerAllowanceDTO> getSalaryAllowance(Long positionId, Long SalaryStepId) {
        QPositionSalaryStep positionSalaryStep = QPositionSalaryStep.positionSalaryStep;
        QPositionSalaryStepAllowance positionSalaryStepAllowance = QPositionSalaryStepAllowance.positionSalaryStepAllowance;
        QSalaryStep salaryStep = QSalaryStep.salaryStep;
        QAllowance allowance = QAllowance.allowance;

        return queryFactory.select(
                Projections.constructor(SalaryLedgerAllowanceDTO.class,
                        allowance.code,
                        allowance.allowance.name,
                        allowance.allowance.taxType,
                        allowance.limitAmount,
                        positionSalaryStepAllowance.amount)
                )
                .from(positionSalaryStep)
                .join(positionSalaryStepAllowance).on(positionSalaryStepAllowance.positionSalaryStep.id.eq(
                        positionSalaryStep.id))
                .join(allowance).on(allowance.id.eq(positionSalaryStepAllowance.allowance.id))
                .join(salaryStep).on(positionSalaryStep.salarySteps.id.eq(salaryStep.id))
                .where(positionSalaryStep.positions.id.eq(positionId)
                        .and(salaryStep.id.eq(SalaryStepId))
                        .and(positionSalaryStep.useEndDate.isNull()))
                .fetch();
    }
}
