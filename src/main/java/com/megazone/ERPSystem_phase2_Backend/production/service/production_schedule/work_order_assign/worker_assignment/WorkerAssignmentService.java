package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentSummaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface WorkerAssignmentService {
    List<WorkerAssignmentDTO> getAllWorkcentersWorkerCount();

    List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(String workcenterCode);

    WorkerAssignmentSummaryDTO getWorkerAssignmentsByDate(LocalDate date, boolean includeShiftType, Long shiftTypeId);
    List<WorkerAssignmentDTO> getWorkerAssignmentsByDateRange(LocalDate startOfMonth, LocalDate endOfMonth);
    WorkerAssignmentSummaryDTO getTodayWorkerAssignments(LocalDate today, boolean includeShiftType, Long shiftTypeId);
    WorkerAssignmentSummaryDTO getTodayWorkerAssignmentsSummary(LocalDate currentDate, boolean includeShiftType, Long shiftTypeId);


    boolean isWorkerAlreadyAssigned(Long workerId, LocalDate date);


    WorkerAssignmentSummaryDTO getWorkerAssignmentsByWorkOrder(Long workOrderId, boolean includeShiftType, Long shiftTypeId);

    List<WorkerAssignmentDTO> getWorkerAssignmentsByWorker(Long workerId, boolean includeShiftType, Long shiftTypeId);

}
