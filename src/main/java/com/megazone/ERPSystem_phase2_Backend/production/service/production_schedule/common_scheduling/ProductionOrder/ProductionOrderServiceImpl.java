package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy.PlanOfMakeToOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy.PlanOfMakeToStockRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
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
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final WorkerAssignmentRepository workerAssignmentRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final WorkerRepository workerRepository;
    private final WorkcenterRepository workcenterRepository;
    private final ShiftTypeRepository shiftTypeRepository;
    private final PlanOfMakeToOrderRepository planOfMakeToOrderRepository;
    private final PlanOfMakeToStockRepository planOfMakeToStockRepository;

    /**
     * 작업 지시 조회 by ID
     */
    @Override
    public ProductionOrderDTO getProductionOrderById(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        return convertToDTO(productionOrder);
    }

    /**
     * 모든 작업 지시 조회
     */
    @Override
    public List<ProductionOrderDTO> getAllProductionOrders() {
        List<ProductionOrder> productionOrders = productionOrderRepository.findAll();
        return productionOrders.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * 작업 지시 생성
     */
    @Override
    public ProductionOrderDTO createProductionOrder(ProductionOrderDTO productionOrderDTO) {
        ProductionOrder productionOrder = convertToEntity(productionOrderDTO);
        ProductionOrder savedProductionOrder = productionOrderRepository.save(productionOrder);
        return convertToDTO(savedProductionOrder);
    }

    /**
     * 작업 지시 수정
     */
    @Override
    public ProductionOrderDTO updateProductionOrder(Long productionOrderId, ProductionOrderDTO productionOrderDTO) {
        ProductionOrder existingProductionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        // 기존 작업지시 업데이트
        updateProductionOrderEntity(existingProductionOrder, productionOrderDTO);
        productionOrderRepository.save(existingProductionOrder);
        return convertToDTO(existingProductionOrder);
    }

    /**
     * 작업 지시 삭제
     */
    @Override
    public void deleteProductionOrder(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        productionOrderRepository.delete(productionOrder);
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
    public void assignWorkersToWorkcenter(ProductionOrderDTO productionOrderDTO) {
        // 작업지시 조회
        ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));

        // 작업자 배정 로직을 Stream API로 변환
        productionOrderDTO.getWorkerAssignments().stream()
                .filter(assignmentDTO -> {
                    // 중복 배정 검증
                    List<WorkerAssignment> existingAssignments = workerAssignmentRepository.findAssignmentsByWorkerAndProductionOrderAndDate(
                            assignmentDTO.getWorkerId(), productionOrder.getId(), assignmentDTO.getAssignmentDate());
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
                            newAssignment.setProductionOrder(productionOrder);
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
    private void updateProductionOrderEntity(ProductionOrder productionOrder, ProductionOrderDTO productionOrderDTO) {
        if (productionOrderDTO.getName() != null) {
            productionOrder.setName(productionOrderDTO.getName());
        }
//        if (productionOrderDTO.getPlanOfMakeToOrderId() != null) {
//            productionOrder.setPlanOfMakeToOrderId(productionOrderDTO.getPlanOfMakeToOrderId());
//        }
//        if (productionOrderDTO.getPlanOfMakeToStockId() != null) {
//            productionOrder.setPlanOfMakeToStockId(productionOrderDTO.getPlanOfMakeToStockId());
//        }
        if (productionOrderDTO.getRemarks() != null) {
            productionOrder.setRemarks(productionOrderDTO.getRemarks());
        }

        // 작업자 배정 리스트 업데이트
        List<WorkerAssignment> updatedAssignments = productionOrderDTO.getWorkerAssignments().stream()
                .map(this::convertWorkerAssignmentDTOToEntity)
                .toList();
        productionOrder.setWorkerAssignments(updatedAssignments);
    }

    // 엔티티를 DTO로 변환
    private ProductionOrderDTO convertToDTO(ProductionOrder productionOrder) {
        return ProductionOrderDTO.builder()
                .id(productionOrder.getId())
                .name(productionOrder.getName())
                .planOfMakeToOrderId(productionOrder.getPlanOfMakeToOrder() != null ? productionOrder.getPlanOfMakeToOrder().getId() : null)
                .planOfMakeToStockId(productionOrder.getPlanOfMakeToStock() != null ? productionOrder.getPlanOfMakeToStock().getId() : null)
                .workerAssignments(productionOrder.getWorkerAssignments().stream()
                        .map(this::convertWorkerAssignmentToDTO)
                        .toList())
                .remarks(productionOrder.getRemarks())
                .build();
    }

    // DTO를 엔티티로 변환
    private ProductionOrder convertToEntity(ProductionOrderDTO productionOrderDTO) {
        return ProductionOrder.builder()
                .id(productionOrderDTO.getId())
                .name(productionOrderDTO.getName())
                .planOfMakeToOrder(productionOrderDTO.getPlanOfMakeToOrderId() != null ? planOfMakeToOrderRepository.findById(productionOrderDTO.getPlanOfMakeToOrderId())
                        .orElseThrow(() -> new EntityNotFoundException("생산 주문 계획을 찾을 수 없습니다.")) : null)
                .planOfMakeToStock(productionOrderDTO.getPlanOfMakeToStockId() != null ? planOfMakeToStockRepository.findById(productionOrderDTO.getPlanOfMakeToStockId())
                        .orElseThrow(() -> new EntityNotFoundException("생산 재고 계획을 찾을 수 없습니다.")) : null)
                .workerAssignments(productionOrderDTO.getWorkerAssignments().stream()
                        .map(this::convertWorkerAssignmentDTOToEntity)
                        .toList())
                .remarks(productionOrderDTO.getRemarks())
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
