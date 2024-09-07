package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.work_order;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.work_order.WorkOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment.WorkerAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkerAssignmentRepository workerAssignmentRepository;
    private final WorkOrderRepository workOrderRepository;

    @Override
    public WorkOrderDTO getWorkOrderById(Long workOrderId) {
        return null;
    }

    @Override
    public List<WorkOrderDTO> getAllWorkOrders() {
        return List.of();
    }

    @Override
    public WorkOrderDTO createWorkOrder(WorkOrderDTO workOrderDTO) {
        return null;
    }

    @Override
    public WorkOrderDTO updateWorkOrder(Long workOrderId, WorkOrderDTO workOrderDTO) {
        return null;
    }

    @Override
    public void deleteWorkOrder(Long workOrderId) {

    }

//    // 작업자 배정 로직
//    public WorkerAssignment assignWorkerToShift(Long workerId, String workcenterCode, Long shiftTypeId, LocalDate assignmentDate) {
//        // 중복 배정 체크
//        if (isWorkerAlreadyAssigned(workerId, assignmentDate)) {
//            throw new IllegalArgumentException("해당 작업자는 이미 동일한 날짜에 배정되었습니다.");
//        }
//
//        // 작업자, 작업장, 교대근무유형을 조회하고 예외처리
//        Worker worker = workerRepository.findById(workerId)
//                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
//        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
//                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
//        ShiftType shiftType = shiftTypeRepository.findById(shiftTypeId)
//                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));
//
//        // 작업자 배정 엔티티 생성 및 저장
//        WorkerAssignment workerAssignment = new WorkerAssignment();
//        workerAssignment.setWorker(worker);
//        workerAssignment.setWorkcenter(workcenter);
//        workerAssignment.setShiftType(shiftType);
//        workerAssignment.setAssignmentDate(assignmentDate);
//
//        return workerAssignment;
//    }

    public void assignWorkersToWorkcenter(WorkOrderDTO workOrderDTO) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("작업 지시를 찾을 수 없습니다."));

        workOrderDTO.getWorkerAssignments().forEach(assignmentDTO -> {
            // 중복 배정 검증
            List<WorkerAssignment> existingAssignments = workerAssignmentRepository.findAssignmentsByWorkerAndWorkOrderAndDate(
                    assignmentDTO.getWorkerId(), workOrder.getId(), assignmentDTO.getAssignmentDate());

            if (!existingAssignments.isEmpty()) {
                throw new IllegalArgumentException("이미 동일한 날짜에 해당 작업자가 배정되었습니다.");
            }

            // 새로운 작업자 배정 로직 처리
            WorkerAssignment newAssignment = new WorkerAssignment();
            // 필요한 필드 설정
            workerAssignmentRepository.save(newAssignment);
        });
    }

    public WorkOrderDTO findById(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        return convertToDTO(workOrder);
    }

    public WorkOrder save(WorkOrderDTO workOrderDTO) {
        WorkOrder workOrder = convertToEntity(workOrderDTO);
        return workOrderRepository.save(workOrder);
    }

    private WorkOrderDTO convertToDTO(WorkOrder workOrder) {
        // DTO 변환 로직
        // ...
        return null;
    }

    private WorkOrder convertToEntity(WorkOrderDTO workOrderDTO) {
        // 엔티티 변환 로직
        // ...
        return null;
    }
}

