package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.WorkerAssignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentCountDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentSummaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface WorkerAssignmentService {
    List<WorkerAssignmentCountDTO> getAllWorkcentersWorkerCount();

    List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(String workcenterCode);

    List<WorkerAssignmentDTO> getWorkerAssignmentsByDate(LocalDate date, boolean includeShiftType, Long shiftTypeId);
    List<WorkerAssignmentDTO> getWorkerAssignmentsByDateRange(LocalDate startOfMonth, LocalDate endOfMonth);
    List<WorkerAssignmentDTO> getTodayWorkerAssignments(LocalDate today, boolean includeShiftType, Long shiftTypeId);
    WorkerAssignmentSummaryDTO getTodayWorkerAssignmentsSummary(LocalDate currentDate, boolean includeShiftType, Long shiftTypeId);


    boolean isWorkerAlreadyAssigned(Long workerId, LocalDate date);


    WorkerAssignmentSummaryDTO getWorkerAssignmentsByProductionOrder(Long productionOrderId, boolean includeShiftType, Long shiftTypeId);

    List<WorkerAssignmentDTO> getWorkerAssignmentsByWorker(Long workerId, boolean includeShiftType, Long shiftTypeId);

}
