package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter.workcenter;
import static com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.QWorkOrder.workOrder;
import static com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorker.worker;
import static com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QProcessDetails.processDetails;

@Repository
@RequiredArgsConstructor
public class WorkerAssignmentRepositoryImpl implements WorkerAssignmentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;

    @Override
    public boolean existsByWorkerIdAndAssignmentDate(Long workerId, LocalDate date) {
        return false;
    }

    @Override
    public List<WorkerAssignment> findWorkerCountByWorkcenter() {
        return List.of();
    }

    @Override
    public Optional<WorkerAssignment> findByWorkcenterCode(String workcenterCode) {
        return Optional.empty();
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
    public List<WorkerAssignment> getWorkerAssignments(String workcenterCode, Optional<LocalDate> optionalDate) {
        // 오늘 날짜가 있을 경우, 해당 날짜에 맞는 작업자 배정을 조회
        if (optionalDate.isPresent()) {
            LocalDate date = optionalDate.get();
            return queryFactory
                    .selectFrom(workerAssignment)
                    .join(workerAssignment.workcenter, workcenter) // 작업장과 WorkerAssignment를 연결하는 Join
                    .fetchJoin() // 데이터 함께 가져오기
                    .where(workerAssignment.workcenter.code.eq(workcenterCode)
                            .and(workerAssignment.assignmentDate.eq(date))) // 오늘 날짜 기준
                    .fetch();
        } else {
            // 날짜가 없을 경우, 전체 작업자 배정을 날짜 순으로 조회
            return queryFactory
                    .selectFrom(workerAssignment)
                    .where(workerAssignment.workcenter.code.eq(workcenterCode))
                    .orderBy(workerAssignment.assignmentDate.desc()) // 최신 날짜 순 정렬
                    .fetch();
        }
    }

    public List<WorkerAssignment> findWorkerAssignmentsByWorkcenterIdAndDate(String workcenterCode, LocalDate date) {
        return queryFactory
                .selectFrom(workerAssignment)
                .join(workerAssignment.worker, worker)
                .join(workerAssignment.workOrder, workOrder)
                .join(workcenter.processDetails, processDetails)
                .fetchJoin()
                .where(workerAssignment.workcenter.code.eq(workcenterCode)
                        .and(workerAssignment.assignmentDate.eq(date)))
                .fetch();
    }
}
