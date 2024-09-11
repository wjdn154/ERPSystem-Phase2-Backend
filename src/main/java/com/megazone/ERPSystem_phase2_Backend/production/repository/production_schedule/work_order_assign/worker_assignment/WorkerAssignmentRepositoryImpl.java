package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.QWorkerAssignment.workerAssignment;

@Repository
@RequiredArgsConstructor
public class WorkerAssignmentRepositoryImpl implements WorkerAssignmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WorkerAssignment> findWorkerCountByWorkcenter() {
        return queryFactory
                .selectFrom(workerAssignment)
                .groupBy(workerAssignment.workcenter)
                .fetch();
    }

    @Override
    public Optional<WorkerAssignment> findByWorkcenterCode(String workcenterCode) {
        WorkerAssignment assignment = queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.workcenter.code.eq(workcenterCode))
                .fetchFirst();
        return Optional.ofNullable(assignment);
    }

    @Override
    public List<WorkerAssignment> findAssignmentsByWorkerAndWorkOrderAndDate(Long workerId, Long workOrderId, LocalDate assignmentDate) {
        return queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.worker.id.eq(workerId)
                        .and(workerAssignment.workOrder.id.eq(workOrderId))
                        .and(workerAssignment.assignmentDate.eq(assignmentDate)))
                .fetch();
    }

    @Override
    public boolean existsByWorkerIdAndAssignmentDate(Long workerId, LocalDate date) {
        Integer count = queryFactory
                .selectOne()
                .from(workerAssignment)
                .where(workerAssignment.worker.id.eq(workerId)
                        .and(workerAssignment.assignmentDate.eq(date)))
                .fetchFirst();
        return count != null;
    }

    @Override
    public List<WorkerAssignment> getWorkerAssignments(String workcenterCode, Optional<LocalDate> optionalDate) {
        var query = queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.workcenter.code.eq(workcenterCode));

        optionalDate.ifPresent(date -> query.where(workerAssignment.assignmentDate.eq(date)));

        return query.fetch();
    }

    @Override
    public List<WorkerAssignment> findWorkerAssignmentsByWorkcenterCodeAndDate(String workcenterCode, LocalDate date) {
        return queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.workcenter.code.eq(workcenterCode)
                        .and(workerAssignment.assignmentDate.eq(date)))
                .fetch();
    }

    @Override
    public List<WorkerAssignmentDTO> findTodayWorkers(String code) {
        return queryFactory
                .select(Projections.constructor(WorkerAssignmentDTO.class,
                        workerAssignment.worker.id,
                        workerAssignment.worker.employee.lastName,
                        workerAssignment.worker.employee.firstName,
                        workerAssignment.worker.employee.employeeNumber,
                        workerAssignment.shiftType.name,
                        workerAssignment.assignmentDate,
                        workerAssignment.workcenter.code))
                .from(workerAssignment)
                .where(workerAssignment.workcenter.code.eq(code)
                        .and(workerAssignment.assignmentDate.eq(LocalDate.now())))
                .fetch();
    }

    @Override
    public Optional<WorkerAssignment> findByWorkerIdAndAssignmentDate(Long workerId, LocalDate assignmentDate) {
        WorkerAssignment assignment = queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.worker.id.eq(workerId)
                        .and(workerAssignment.assignmentDate.eq(assignmentDate)))
                .fetchFirst();
        return Optional.ofNullable(assignment);
    }
}
