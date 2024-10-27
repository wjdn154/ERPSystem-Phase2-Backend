package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDateDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
