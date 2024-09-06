package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkerAssignmentRepositoryImpl implements WorkerAssignmentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;

    @Override
    public List<WorkerAssignmentRepository> findWorkerCountByWorkcenter() {
        return List.of();
    }

    @Override
    public Collection<Object> findByWorkcenterCode(String workcenterCode) {
        return List.of();
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
}
