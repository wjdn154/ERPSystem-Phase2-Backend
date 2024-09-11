package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.work_order;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.plan_of_production.PlanOfMakeToOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.plan_of_production.PlanOfMakeToStockRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.work_order.WorkOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment.WorkerAssignmentRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
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
    private final WorkerRepository workerRepository;
    private final WorkcenterRepository workcenterRepository;
    private final ShiftTypeRepository shiftTypeRepository;
    private final PlanOfMakeToOrderRepository planOfMakeToOrderRepository;
    private final PlanOfMakeToStockRepository planOfMakeToStockRepository;

    /**
     * 작업 지시 조회 by ID
     */
    @Override
    public WorkOrderDTO getWorkOrderById(Long workOrderId) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        return convertToDTO(workOrder);
    }

    /**
     * 모든 작업 지시 조회
     */
    @Override
    public List<WorkOrderDTO> getAllWorkOrders() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return workOrders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * 작업 지시 생성
     */
    @Override
    public WorkOrderDTO createWorkOrder(WorkOrderDTO workOrderDTO) {
        WorkOrder workOrder = convertToEntity(workOrderDTO);
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);
        return convertToDTO(savedWorkOrder);
    }

    /**
     * 작업 지시 수정
     */
    @Override
    public WorkOrderDTO updateWorkOrder(Long workOrderId, WorkOrderDTO workOrderDTO) {
        WorkOrder existingWorkOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        // 기존 작업지시 업데이트
        updateWorkOrderEntity(existingWorkOrder, workOrderDTO);
        workOrderRepository.save(existingWorkOrder);
        return convertToDTO(existingWorkOrder);
    }

    /**
     * 작업 지시 삭제
     */
    @Override
    public void deleteWorkOrder(Long workOrderId) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        workOrderRepository.delete(workOrder);
    }

    /**
     * 작업자 배정 로직
     */
    public WorkerAssignment assignWorkerToShift(Long workerId, String workcenterCode, Long shiftTypeId, LocalDate assignmentDate) {
        // 중복 배정 체크
        if (isWorkerAlreadyAssigned(workerId, assignmentDate)) {
            throw new IllegalArgumentException("해당 작업자는 이미 동일한 날짜에 배정되었습니다.");
        }

        // 작업자, 작업장, 교대근무유형을 조회하고 예외처리
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
        ShiftType shiftType = shiftTypeRepository.findById(shiftTypeId)
                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));

        // 작업자 배정 엔티티 생성 및 저장
        WorkerAssignment workerAssignment = new WorkerAssignment();
        workerAssignment.setWorker(worker);
        workerAssignment.setWorkcenter(workcenter);
        workerAssignment.setShiftType(shiftType);
        workerAssignment.setAssignmentDate(assignmentDate);
        return workerAssignmentRepository.save(workerAssignment);
    }

    /**
     * 여러 작업자 배정 로직
     */
    public void assignWorkersToWorkcenter(WorkOrderDTO workOrderDTO) {
        // 작업지시 조회
        WorkOrder workOrder = workOrderRepository.findById(workOrderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));

        // 작업자 배정 로직을 Stream API로 변환
        workOrderDTO.getWorkerAssignments().stream()
                .filter(assignmentDTO -> {
                    // 중복 배정 검증
                    List<WorkerAssignment> existingAssignments = workerAssignmentRepository.findAssignmentsByWorkerAndWorkOrderAndDate(
                            assignmentDTO.getWorkerId(), workOrder.getId(), assignmentDTO.getAssignmentDate());
                    return existingAssignments.isEmpty(); // 중복이 없으면 통과
                })
                .forEach(assignmentDTO -> {
                    // 새로운 작업자 배정 로직 처리
                    WorkerAssignment newAssignment;
                    try {
                        // Shift가 null이 아닌지 체크 후 처리
                        if (assignmentDTO.getShiftTypeId() != null) {
                            newAssignment = assignWorkerToShift(
                                    assignmentDTO.getWorkerId(),        // workerId
                                    assignmentDTO.getWorkcenterCode(),  // workcenterCode
                                    assignmentDTO.getShiftTypeId(),   // shiftTypeId
                                    assignmentDTO.getAssignmentDate()   // assignmentDate
                            );
                            newAssignment.setWorkOrder(workOrder);
                            workerAssignmentRepository.save(newAssignment);
                        } else {
                            throw new IllegalArgumentException("Shift 정보가 누락되었습니다.");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("작업자 배정 중 오류 발생: " + e.getMessage());
                    }
                });
    }


    // 작업자가 이미 해당 날짜에 배정되었는지 확인하는 메서드
    private boolean isWorkerAlreadyAssigned(Long workerId, LocalDate assignmentDate) {
        return !workerAssignmentRepository.findByWorkerIdAndAssignmentDate(workerId, assignmentDate).isEmpty();
    }

    // 기존 엔티티 업데이트
    private void updateWorkOrderEntity(WorkOrder workOrder, WorkOrderDTO workOrderDTO) {
        if (workOrderDTO.getName() != null) {
            workOrder.setName(workOrderDTO.getName());
        }
//        if (workOrderDTO.getPlanOfMakeToOrderId() != null) {
//            workOrder.setPlanOfMakeToOrderId(workOrderDTO.getPlanOfMakeToOrderId());
//        }
//        if (workOrderDTO.getPlanOfMakeToStockId() != null) {
//            workOrder.setPlanOfMakeToStockId(workOrderDTO.getPlanOfMakeToStockId());
//        }
        if (workOrderDTO.getRemarks() != null) {
            workOrder.setRemarks(workOrderDTO.getRemarks());
        }

        // 작업자 배정 리스트 업데이트
        List<WorkerAssignment> updatedAssignments = workOrderDTO.getWorkerAssignments().stream()
                .map(this::convertWorkerAssignmentDTOToEntity)
                .toList();
        workOrder.setWorkerAssignments(updatedAssignments);
    }

    // 엔티티를 DTO로 변환
    private WorkOrderDTO convertToDTO(WorkOrder workOrder) {
        return WorkOrderDTO.builder()
                .id(workOrder.getId())
                .name(workOrder.getName())
                .planOfMakeToOrderId(workOrder.getPlanOfMakeToOrder() != null ? workOrder.getPlanOfMakeToOrder().getId() : null)
                .planOfMakeToStockId(workOrder.getPlanOfMakeToStock() != null ? workOrder.getPlanOfMakeToStock().getId() : null)
                .workerAssignments(workOrder.getWorkerAssignments().stream()
                        .map(this::convertWorkerAssignmentToDTO)
                        .toList())
                .remarks(workOrder.getRemarks())
                .build();
    }

    // DTO를 엔티티로 변환
    private WorkOrder convertToEntity(WorkOrderDTO workOrderDTO) {
        return WorkOrder.builder()
                .id(workOrderDTO.getId())
                .name(workOrderDTO.getName())
                .planOfMakeToOrder(workOrderDTO.getPlanOfMakeToOrderId() != null ? planOfMakeToOrderRepository.findById(workOrderDTO.getPlanOfMakeToOrderId())
                        .orElseThrow(() -> new EntityNotFoundException("생산 주문 계획을 찾을 수 없습니다.")) : null)
                .planOfMakeToStock(workOrderDTO.getPlanOfMakeToStockId() != null ? planOfMakeToStockRepository.findById(workOrderDTO.getPlanOfMakeToStockId())
                        .orElseThrow(() -> new EntityNotFoundException("생산 재고 계획을 찾을 수 없습니다.")) : null)
                .workerAssignments(workOrderDTO.getWorkerAssignments().stream()
                        .map(this::convertWorkerAssignmentDTOToEntity)
                        .toList())
                .remarks(workOrderDTO.getRemarks())
                .build();
    }

    // 작업자 배정 DTO를 엔티티로 변환하는 메서드
    private WorkerAssignment convertWorkerAssignmentDTOToEntity(WorkerAssignmentDTO workerAssignmentDTO) {
        return WorkerAssignment.builder()
                .worker(workerRepository.findById(workerAssignmentDTO.getWorkerId())
                        .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."))
                )
                .workcenter(workcenterRepository.findByCode(workerAssignmentDTO.getWorkcenterCode())
                        .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."))
                )
                .shiftType(shiftTypeRepository.findById(workerAssignmentDTO.getShiftTypeId())
                        .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."))
                )
                .assignmentDate(workerAssignmentDTO.getAssignmentDate())
                .build();
    }

    // 작업자 배정 엔티티를 DTO로 변환하는 메서드
    private WorkerAssignmentDTO convertWorkerAssignmentToDTO(WorkerAssignment workerAssignment) {
        return WorkerAssignmentDTO.builder()
                .workerId(workerAssignment.getWorker().getId())
                .workcenterCode(workerAssignment.getWorkcenter().getCode())
                .shiftTypeId(workerAssignment.getShiftType().getId())
                .assignmentDate(workerAssignment.getAssignmentDate())
                .build();
    }

}
