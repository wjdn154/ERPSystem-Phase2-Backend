//package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order;
//
//import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkOrder;
//import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkOrderDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order.WorkOrderRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker_assignments.WorkerAssignmentRepository;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class WorkOrderServiceImpl implements WorkOrderService {
//
//    private final WorkerAssignmentRepository workerAssignmentRepository;
//    private final WorkOrderRepository workOrderRepository;
//
//    public void assignWorkersToWorkcenter(WorkOrderDTO workOrderDTO) {
//        WorkOrder workOrder = workOrderRepository.findById(workOrderDTO.getId())
//                .orElseThrow(() -> new EntityNotFoundException("작업 지시를 찾을 수 없습니다."));
//
//        workOrderDTO.getWorkerAssignments().forEach(assignmentDTO -> {
//            // 중복 배정 검증
//            List<WorkerAssignment> existingAssignments = workerAssignmentRepository.findAssignmentsByWorkerAndWorkOrderAndDate(
//                    assignmentDTO.getWorkerId(), workOrder.getId(), assignmentDTO.getAssignmentDate());
//
//            if (!existingAssignments.isEmpty()) {
//                throw new IllegalArgumentException("이미 동일한 날짜에 해당 작업자가 배정되었습니다.");
//            }
//
//            // 새로운 작업자 배정 로직 처리
//            WorkerAssignment newAssignment = new WorkerAssignment();
//            // 필요한 필드 설정
//            workerAssignmentRepository.save(newAssignment);
//        });
//    }
//
//    public WorkOrderDTO findById(Long id) {
//        WorkOrder workOrder = workOrderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
//        return convertToDTO(workOrder);
//    }
//
//    public WorkOrder save(WorkOrderDTO workOrderDTO) {
//        WorkOrder workOrder = convertToEntity(workOrderDTO);
//        return workOrderRepository.save(workOrder);
//    }
//
//    private WorkOrderDTO convertToDTO(WorkOrder workOrder) {
//        // DTO 변환 로직
//        // ...
//        return null;
//    }
//
//    private WorkOrder convertToEntity(WorkOrderDTO workOrderDTO) {
//        // 엔티티 변환 로직
//        // ...
//        return null;
//    }
//}
//
