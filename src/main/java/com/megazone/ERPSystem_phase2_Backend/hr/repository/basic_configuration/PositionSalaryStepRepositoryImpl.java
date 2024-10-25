package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.*;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.PositionSalaryStepSearchDTO;
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
                                qPositionSalaryStep.useStartDate.month(),
                                qPositionSalaryStep.useEndDate.month()
                        )
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(qPositionSalaryStep.positions.id.eq(positionId))
                .fetch();
    }

    @Override
    public List<PositionSalaryStepDTO> endDateShow(PositionSalaryStepSearchDTO dto) {
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
                                qPositionSalaryStep.useStartDate.month(),
                                qPositionSalaryStep.useEndDate.month()
                        )
                )
                .from(qPositionSalaryStep)
                .join(qPositionSalaryStepAllowance).on(qPositionSalaryStepAllowance.positionSalaryStep.id.eq(
                        qPositionSalaryStep.id))
                .join(qAllowance).on(qAllowance.id.eq(qPositionSalaryStepAllowance.allowance.id))
                .join(qSalaryStep).on(qPositionSalaryStep.salarySteps.id.eq(qSalaryStep.id))
                .where(qPositionSalaryStep.positions.id.eq(dto.getPositionId())
                        .and(qPositionSalaryStep.useEndDate.eq(dto.getEndMonth())))
                .fetch();
    }
}
