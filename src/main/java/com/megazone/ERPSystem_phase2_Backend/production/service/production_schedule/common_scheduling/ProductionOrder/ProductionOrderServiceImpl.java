package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToStock;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy.mto.PlanOfMakeToOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.production_strategy.mts.PlanOfMakeToStockRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final WorkerAssignmentRepository workerAssignmentRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final WorkerRepository workerRepository;
    private final WorkcenterRepository workcenterRepository;
    private final ShiftTypeRepository shiftTypeRepository;
    private final WorkPerformanceRepository workPerformanceRepository;
    private final ProductRepository productRepository;
//    private final PlanOfMakeToOrderRepository planOfMakeToOrderRepository;
//    private final PlanOfMakeToStockRepository planOfMakeToStockRepository;

    // MPS로부터 자동으로 ProductionOrder 생성
    public void createOrdersFromMps(Mps mps) {
        ProductionOrder order = ProductionOrder.builder()
                .name(mps.getName() + " 작업 지시")
                .mps(mps)
                .startDateTime(mps.getStartDate().atStartOfDay())
                .endDateTime(mps.getEndDate().atStartOfDay())
                .closed(false) // 초기에는 미마감
                .build();

        productionOrderRepository.save(order);
    }

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
    public ProductionOrderDTO saveProductionOrder(ProductionOrderDTO productionOrderDTO) {
        ProductionOrder productionOrder = convertToEntity(productionOrderDTO);
        ProductionOrder savedProductionOrder = productionOrderRepository.save(productionOrder);

        assignWorkersToWorkcenter(productionOrderDTO, savedProductionOrder);

        return convertToDTO(savedProductionOrder);
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
    @Override
    public void assignWorkersToWorkcenter(ProductionOrderDTO productionOrderDTO, ProductionOrder productionOrder) {
        productionOrderDTO.getWorkerAssignments().forEach(assignmentDTO -> {
            // 1. 같은 날 다른 작업장 중복 배정 여부 확인
            Optional<WorkerAssignment> existingAssignment = workerAssignmentRepository.findByWorkerIdAndAssignmentDate(
                    assignmentDTO.getWorkerId(),
                    assignmentDTO.getAssignmentDate()
            );
            if (existingAssignment.isPresent() &&
                    !existingAssignment.get().getWorkcenter().getCode().equals(assignmentDTO.getWorkcenterCode())) {
                throw new IllegalArgumentException("이미 다른 작업장에 배정된 작업자입니다.");
            }

            // 2. 같은 날 다른 교대 유형 중복 배정 여부 확인
            List<WorkerAssignment> existingShiftAssignments = workerAssignmentRepository.findByAssignmentDateAndShiftTypeId(
                    assignmentDTO.getAssignmentDate(),
                    assignmentDTO.getShiftTypeId()
            );
            if (!existingShiftAssignments.isEmpty() && existingShiftAssignments.stream()
                    .anyMatch(assignment -> assignment.getWorker().getId().equals(assignmentDTO.getWorkerId()))) {
                throw new IllegalArgumentException("다른 교대유형에 배정된 작업자입니다.");
            }

            // 3. 작업자 배정 불가 여부 확인 (휴가, 연차, 퇴사 여부)
            Worker worker = workerRepository.findById(assignmentDTO.getWorkerId())
                    .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));

            if ("퇴사".equals(worker.getEmployee().getEmploymentStatus())) {
                throw new IllegalArgumentException("퇴사한 작업자는 배정할 수 없습니다.");
            }

//            if (leavesRepository.existsByWorkerIdAndDate(assignmentDTO.getWorkerId(), assignmentDTO.getAssignmentDate())) {
//                throw new IllegalArgumentException("해당 작업자는 휴가 중입니다.");
//            }

            // 4. 작업자 배정 처리
            WorkerAssignment workerAssignment = assignWorkerToShift(
                    assignmentDTO.getWorkerId(),
                    assignmentDTO.getWorkcenterCode(),
                    assignmentDTO.getShiftTypeId(),
                    assignmentDTO.getAssignmentDate(),
                    productionOrder
            );
            workerAssignmentRepository.save(workerAssignment);
        });
    }

    /**
     * 작업자 배정 엔티티 생성
     */
    private WorkerAssignment assignWorkerToShift(Long workerId, String workcenterCode, Long shiftTypeId, LocalDate assignmentDate, ProductionOrder productionOrder) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
        ShiftType shiftType = shiftTypeRepository.findById(shiftTypeId)
                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));

        return WorkerAssignment.builder()
                .worker(worker)
                .workcenter(workcenter)
                .shiftType(shiftType)
                .assignmentDate(assignmentDate)
                .productionOrder(productionOrder)
                .build();
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

        // 작업자 배정 로직 추가 (수정 시에도 배정 변경 반영)
        assignWorkersToWorkcenter(productionOrderDTO, existingProductionOrder);

        productionOrderRepository.save(existingProductionOrder);
        return convertToDTO(existingProductionOrder);
    }

    private void updateProductionOrderEntity(ProductionOrder productionOrder, ProductionOrderDTO productionOrderDTO) {
        // Name이 null이 아니면 업데이트
        if (productionOrderDTO.getName() != null) {
            productionOrder.setName(productionOrderDTO.getName());
        }

//        // PlanOfMakeToOrder가 변경되었을 때만 업데이트
//        if (productionOrderDTO.getPlanOfMakeToOrderId() != null) {
//            PlanOfMakeToOrder planOfMakeToOrder = planOfMakeToOrderRepository.findById(productionOrderDTO.getPlanOfMakeToOrderId())
//                    .orElseThrow(() -> new EntityNotFoundException("생산 주문 계획을 찾을 수 없습니다."));
//            productionOrder.setPlanOfMakeToOrder(planOfMakeToOrder);
//        }
//
//        // PlanOfMakeToStock이 변경되었을 때만 업데이트
//        if (productionOrderDTO.getPlanOfMakeToStockId() != null) {
//            PlanOfMakeToStock planOfMakeToStock = planOfMakeToStockRepository.findById(productionOrderDTO.getPlanOfMakeToStockId())
//                    .orElseThrow(() -> new EntityNotFoundException("생산 재고 계획을 찾을 수 없습니다."));
//            productionOrder.setPlanOfMakeToStock(planOfMakeToStock);
//        }

        // Remarks가 null이 아니면 업데이트
        if (productionOrderDTO.getRemarks() != null) {
            productionOrder.setRemarks(productionOrderDTO.getRemarks());
        }

        // WorkerAssignment 리스트는 별도로 처리 (assignWorkersToWorkcenter 메서드에서 처리)
        // 부분적으로 WorkerAssignment가 업데이트될 때에는 assignWorkersToWorkcenter 호출
        if (productionOrderDTO.getWorkerAssignments() != null) {
            assignWorkersToWorkcenter(productionOrderDTO, productionOrder);
        }
    }

    /**
     * 작업지시를 마감처리하면 해당 마감한 작업지시에 대한 작업실적 자동 생성
     */
    public WorkPerformanceDTO updateOrderClosure(WorkPerformanceUpdateDTO dto) {
        ProductionOrder productionOrder = productionOrderRepository.findById(dto.getProductionOrderId())
                .orElseThrow(() -> new EntityNotFoundException("작업 지시를 찾을 수 없습니다."));

        if (productionOrder.getClosed()) throw new IllegalArgumentException("이미 마감된 작업 지시입니다.");

        // 작업 지시 마감 처리 (상태만 변경)
        productionOrder.setClosed(true);
        productionOrder.setActualStartDateTime(productionOrder.getStartDateTime());
        productionOrder.setActualEndDateTime(dto.getActualEndDateTime());
        productionOrder.setActualProductionQuantity(dto.getQuantity());
        productionOrder.setActualWorkers(dto.getWorkers());
        productionOrderRepository.save(productionOrder);

        // 작업 지시 마감처리시, 작업종료시간 자동으로 입력되고, 시작시간과의 차이를 구해서 그 시간을 작업실적으로 전달함

        // 작업 실적 생성 로직 추가
        if (dto.getQuantity() == null || dto.getQuantity().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("유효하지 않은 생산 수량입니다.");


        // 작업 실적 생성 로직 추가
        WorkPerformance workPerformance = WorkPerformance.builder()
                .quantity(dto.getQuantity())
                .defectiveQuantity(dto.getDefectiveQuantity())
                .acceptableQuantity(dto.getQuantity().subtract(dto.getDefectiveQuantity()))
                .workDate(dto.getWorkDate())
                .workers(dto.getWorkers())
                .productionOrder(productionOrder)
                .product(productionOrder.getMps().getProduct())
                .build();
        workPerformanceRepository.save(workPerformance);

        return null;
    }


    // 엔티티를 DTO로 변환
    private ProductionOrderDTO convertToDTO(ProductionOrder productionOrder) {
        return null;
//        return ProductionOrderDTO.builder()
//                .id(productionOrder.getId())
//                .name(productionOrder.getName())
////                .planOfMakeToOrderId(productionOrder.getPlanOfMakeToOrder() != null ? productionOrder.getPlanOfMakeToOrder().getId() : null)
////                .planOfMakeToStockId(productionOrder.getPlanOfMakeToStock() != null ? productionOrder.getPlanOfMakeToStock().getId() : null)
//                .workerAssignments(productionOrder.getWorkerAssignments().stream()
//                        .map(this::convertWorkerAssignmentToDTO)
//                        .toList())
//                .remarks(productionOrder.getRemarks())
//                .confirmed(productionOrder.getConfirmed())
//                .startDateTime(productionOrder.getStartDateTime())
//                .endDateTime(productionOrder.getEndDateTime())
//                .build();
    }

    // DTO를 엔티티로 변환
    private ProductionOrder convertToEntity(ProductionOrderDTO productionOrderDTO) {
        return ProductionOrder.builder()
                .id(productionOrderDTO.getId())
                .name(productionOrderDTO.getName())
//                .planOfMakeToOrder(productionOrderDTO.getPlanOfMakeToOrderId() != null ? planOfMakeToOrderRepository.findById(productionOrderDTO.getPlanOfMakeToOrderId())
//                        .orElseThrow(() -> new EntityNotFoundException("생산 주문 계획을 찾을 수 없습니다.")) : null)
//                .planOfMakeToStock(productionOrderDTO.getPlanOfMakeToStockId() != null ? planOfMakeToStockRepository.findById(productionOrderDTO.getPlanOfMakeToStockId())
//                        .orElseThrow(() -> new EntityNotFoundException("생산 재고 계획을 찾을 수 없습니다.")) : null)
                .remarks(productionOrderDTO.getRemarks())
                .confirmed(productionOrderDTO.getConfirmed())
                .startDateTime(productionOrderDTO.getStartDateTime())
                .endDateTime(productionOrderDTO.getEndDateTime())
                .build();
    }

    // 작업자 배정 엔티티를 DTO로 변환
    private WorkerAssignmentDTO convertWorkerAssignmentToDTO(WorkerAssignment workerAssignment) {
        return WorkerAssignmentDTO.builder()
                .workerId(workerAssignment.getWorker().getId())
                .workcenterCode(workerAssignment.getWorkcenter().getCode())
                .shiftTypeId(workerAssignment.getShiftType().getId())
                .assignmentDate(workerAssignment.getAssignmentDate())
                .build();
    }


}
