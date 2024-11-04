package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.EnvironmentalCertificationAssessment;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.EnvironmentalCertificationAssessmentRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomMaterialRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    private final MpsRepository mpsRepository;
    private final ProcessDetailsRepository processDetailsRepository;
    private final StandardBomRepository standardBomRepository;
    private final StandardBomMaterialRepository standardBomMaterialRepository;
    private final EquipmentDataRepository equipmentRepository;
    private final EnvironmentalCertificationAssessmentRepository environmentalCertificationAssessmentRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

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

    @Override
    public boolean isProductionOrderConfirmed(Long id) {
        ProductionOrder productionOrder = productionOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
        productionOrder.setConfirmed(true);
        productionOrderRepository.save(productionOrder);
        return true;
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

    @Override
    public List<ProductionOrderDTO> getUnconfirmedProductionOrders() {
        List<ProductionOrder> productionOrders = productionOrderRepository.findByConfirmedFalse();
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

        recentActivityRepository.save(RecentActivity.builder()
                .activityDescription(savedProductionOrder.getName() + "신규 작업지시 생성")
                .activityType(ActivityType.PRODUCTION)
                .activityTime(LocalDateTime.now())
                .build());

        notificationService.createAndSendNotification(
                ModuleType.PRODUCTION,
                PermissionType.ALL,
                savedProductionOrder.getName() + "신규 작업지시가 생성되었습니다..",
                NotificationType.UPDATE_PRODUCTION_ORDER);

//        assignWorkersToWorkcenter(productionOrderDTO, savedProductionOrder);

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
    public void updateOrderClosure(WorkPerformanceUpdateDTO dto) {
        // 검증 및 마감 처리
        ProductionOrder productionOrder = productionOrderRepository.findById(dto.getProductionOrderId()).orElseThrow(() -> new EntityNotFoundException("작업 지시를 찾을 수 없습니다."));

        if (productionOrder.getClosed()) throw new IllegalArgumentException("이미 마감된 작업 지시입니다.");
        if (dto.getQuantity() == null || dto.getQuantity().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("유효하지 않은 생산 수량입니다.");

        productionOrder.setClosed(true);
        productionOrder.setActualStartDateTime(dto.getActualStartDateTime());
        productionOrder.setActualEndDateTime(dto.getActualEndDateTime());
        productionOrder.setActualProductionQuantity(dto.getQuantity());
        productionOrder.setActualWorkers(dto.getWorkers());
        productionOrderRepository.save(productionOrder);

        // 작업 시간 계산
        Duration workDuration = Duration.between(productionOrder.getStartDateTime(), productionOrder.getEndDateTime());
        BigDecimal workHours = BigDecimal.valueOf(workDuration.toHours());

        // 폐기물 발생량 계산
        BigDecimal averageWasteGenerated = calculateWaste(productionOrder, dto.getQuantity());
        BigDecimal actualWasteGenerated = averageWasteGenerated.multiply(BigDecimal.valueOf(0.95 + Math.random() * 0.1)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal wasteGeneratedPercentage = (averageWasteGenerated.compareTo(BigDecimal.ZERO) != 0) ?
                actualWasteGenerated.divide(averageWasteGenerated, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        // 에너지 소비량 계산
        BigDecimal actualEnergyConsumption = calculateActualEnergy(productionOrder, workHours);
        BigDecimal averageEnergyConsumption = calculateAverageEnergy(productionOrder, dto.getQuantity());
        averageEnergyConsumption = actualEnergyConsumption.multiply(BigDecimal.valueOf(0.95 + Math.random() * 0.1)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal energyConsumedPercentage = (averageEnergyConsumption.compareTo(BigDecimal.ZERO) != 0) ?
                actualEnergyConsumption.divide(averageEnergyConsumption, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        // 작업 실적 생성 및 저장
        WorkPerformance workPerformance = WorkPerformance.builder()
                .quantity(dto.getQuantity())
                .defectiveQuantity(dto.getDefectiveQuantity())
                .acceptableQuantity(dto.getQuantity().subtract(dto.getDefectiveQuantity()))
                .workHours(workHours)
                .workDate(dto.getActualEndDateTime().toLocalDate())
                .workers(dto.getWorkers())
                .productionOrder(productionOrder)
                .industryAverageWasteGenerated(averageWasteGenerated)
                .wasteGenerated(actualWasteGenerated)
                .wasteGeneratedPercentage(wasteGeneratedPercentage)
                .industryAverageEnergyConsumed(averageEnergyConsumption)
                .energyConsumed(actualEnergyConsumption)
                .energyConsumedPercentage(energyConsumedPercentage)
                .build();

        workPerformanceRepository.save(workPerformance);

        Optional<EnvironmentalCertificationAssessment> ECA = environmentalCertificationAssessmentRepository.findByMonth(YearMonth.from(dto.getActualEndDateTime()));

        if (ECA.isEmpty()) {
            Integer wasteScore = calculateWasteScore(actualWasteGenerated, averageWasteGenerated);
            Integer energyScore = calculateEnergyScore(actualEnergyConsumption, averageEnergyConsumption);
            Integer totalScore = (wasteScore + energyScore) / 2;

            EnvironmentalCertificationAssessment newECA = EnvironmentalCertificationAssessment.builder()
                    .month(YearMonth.from(dto.getActualEndDateTime()))
                    .totalWasteGenerated(actualWasteGenerated)
                    .totalEnergyConsumed(actualEnergyConsumption)
                    .totalIndustryAverageWasteGenerated(averageWasteGenerated)
                    .totalIndustryAverageEnergyConsumed(averageEnergyConsumption)
                    .wasteScore(wasteScore)
                    .energyScore(energyScore)
                    .totalScore(totalScore)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();
            environmentalCertificationAssessmentRepository.save(newECA);
        } else {
            EnvironmentalCertificationAssessment existingECA = ECA.get();
            BigDecimal totalWasteGenerated = existingECA.getTotalWasteGenerated().add(actualWasteGenerated);
            BigDecimal totalEnergyConsumed = existingECA.getTotalEnergyConsumed().add(actualEnergyConsumption);
            BigDecimal totalAverageWasteGenerated = existingECA.getTotalIndustryAverageWasteGenerated().add(averageWasteGenerated);
            BigDecimal totalAverageEnergyConsumption = existingECA.getTotalIndustryAverageEnergyConsumed().add(averageEnergyConsumption);
            Integer wasteScore = calculateWasteScore(totalWasteGenerated, totalAverageWasteGenerated);
            Integer energyScore = calculateEnergyScore(totalEnergyConsumed, totalAverageEnergyConsumption);
            Integer totalScore = (wasteScore + energyScore) / 2;

            existingECA.setTotalWasteGenerated(totalWasteGenerated);
            existingECA.setTotalEnergyConsumed(totalEnergyConsumed);
            existingECA.setTotalIndustryAverageWasteGenerated(totalAverageWasteGenerated);
            existingECA.setTotalIndustryAverageEnergyConsumed(totalAverageEnergyConsumption);
            existingECA.setWasteScore(wasteScore);
            existingECA.setEnergyScore(energyScore);
            existingECA.setTotalScore(totalScore);
            existingECA.setModifiedDate(LocalDateTime.now());

            environmentalCertificationAssessmentRepository.save(existingECA);
        }

        recentActivityRepository.save(RecentActivity.builder()
                .activityType(ActivityType.PRODUCTION)
                .activityDescription(productionOrder.getMps().getProduct().getName() + " " + dto.getQuantity().subtract(dto.getDefectiveQuantity()) + " 개 생산 완료, 작업지시 마감 처리 완료")
                .activityTime(LocalDateTime.now())
                .build());
    }

    // 폐기물 발생량 점수 계산 함수
    public Integer calculateWasteScore(BigDecimal actualWaste, BigDecimal averageWaste) {
        BigDecimal wasteRatio = (averageWaste.compareTo(BigDecimal.ZERO) != 0) ?
                actualWaste.divide(averageWaste, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)) :
                BigDecimal.ZERO; // 폐기물 발생 비율 : (실제 폐기물 발생량 / 산업 평균 폐기물 발생량) * 100
        int score;

        if (wasteRatio.compareTo(BigDecimal.valueOf(50)) <= 0) {
            score = 100 - wasteRatio.intValue(); // 50% 이하 : 100점에서 비율만큼 차감
        } else if (wasteRatio.compareTo(BigDecimal.valueOf(70)) <= 0) {
            score = 90 - (wasteRatio.intValue() - 50); // 51~70% : 90점에서 비율 차이에 따라 1점씩 차감
        } else if (wasteRatio.compareTo(BigDecimal.valueOf(90)) <= 0) {
            score = 80 - (wasteRatio.intValue() - 70); // 71~90% : 80점에서 비율 차이에 따라 1점씩 차감
        } else if (wasteRatio.compareTo(BigDecimal.valueOf(110)) <= 0) {
            score = 70 - (wasteRatio.intValue() - 90); // 91~110% : 70점에서 비율 차이에 따라 1점씩 차감
        } else if (wasteRatio.compareTo(BigDecimal.valueOf(130)) <= 0) {
            score = 60 - (wasteRatio.intValue() - 110); // 111~130% : 60점에서 비율 차이에 따라 1점씩 차감
        } else {
            score = 0; // 130% 초과 : 0점
        }

        return Math.max(score, 0); // 음수 방지, 최소 0점
    }

    // 에너지 사용량 점수 계산 함수
    public Integer calculateEnergyScore(BigDecimal actualEnergy, BigDecimal averageEnergy) {
        BigDecimal energyRatio = (averageEnergy.compareTo(BigDecimal.ZERO) != 0) ?
                actualEnergy.divide(averageEnergy, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)) :
                BigDecimal.ZERO; // 에너지 사용 비율 : (실제 에너지 사용량 / 산업 평균 에너지 사용량) * 100
        int score;

        if (energyRatio.compareTo(BigDecimal.valueOf(40)) <= 0) {
            score = 100 - energyRatio.intValue(); // 40% 이하 : 100점에서 비율만큼 차감
        } else if (energyRatio.compareTo(BigDecimal.valueOf(60)) <= 0) {
            score = 90 - (energyRatio.intValue() - 40); // 41~60% : 90점에서 비율 차이에 따라 1점씩 차감
        } else if (energyRatio.compareTo(BigDecimal.valueOf(80)) <= 0) {
            score = 80 - (energyRatio.intValue() - 60); // 61~80% : 80점에서 비율 차이에 따라 1점씩 차감
        } else if (energyRatio.compareTo(BigDecimal.valueOf(100)) <= 0) {
            score = 70 - (energyRatio.intValue() - 80); // 81~100% : 70점에서 비율 차이에 따라 1점씩 차감
        } else if (energyRatio.compareTo(BigDecimal.valueOf(120)) <= 0) {
            score = 60 - (energyRatio.intValue() - 100); // 101~120% : 60점에서 비율 차이에 따라 1점씩 차감
        } else {
            score = 0; // 120% 초과 : 0점
        }

        return Math.max(score, 0); // 음수 방지, 최소 0점
    }

    // 폐기물 발생량 계산 함수
    private BigDecimal calculateWaste(ProductionOrder productionOrder, BigDecimal workQuantity) {
        StandardBom bom = standardBomRepository.findByProductId(productionOrder.getMps().getProduct().getId()).get(0);

        return standardBomMaterialRepository.findByBomId(bom.getId()).stream()
                .map(bomMaterial -> {
                    MaterialData material = bomMaterial.getMaterial();
                    BigDecimal averageWaste = material.getAverageWaste(); // 산업평균 폐기물 발생량
                    Long materialQuantity = material.getQuantity();       // 1kg당 자재 수량
                    Long requiredMaterialQuantity = bomMaterial.getQuantity(); // 1개 품목을 만들기 위한 자재 수량
                    return averageWaste
                            .multiply(BigDecimal.valueOf(workQuantity.longValue() * requiredMaterialQuantity))
                            .divide(BigDecimal.valueOf(materialQuantity), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 실제 에너지 사용량 계산 함수
    private BigDecimal calculateActualEnergy(ProductionOrder productionOrder, BigDecimal workHours) {

        // 장비별 에너지 소비량 계산 후 합산
        return equipmentRepository.findByWorkcenterId(productionOrder.getWorkcenter().getId()).stream()
                .map(equipment -> {
                    BigDecimal equipmentEnergyConsumption = BigDecimal.valueOf(equipment.getKWh())
                            .multiply(workHours)
                            .multiply(BigDecimal.valueOf(3.6));
                    return equipmentEnergyConsumption;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 산업 평균 에너지 사용량 계산 함수
    private BigDecimal calculateAverageEnergy(ProductionOrder productionOrder, BigDecimal workQuantity) {
        StandardBom bom = standardBomRepository.findByProductId(productionOrder.getMps().getProduct().getId()).get(0);
        return standardBomMaterialRepository.findByBomId(bom.getId()).stream()
                .map(bomMaterial -> {
                    MaterialData material = bomMaterial.getMaterial();
                    BigDecimal averageEnergy = material.getAveragePowerConsumption();
                    return averageEnergy
                            .multiply(BigDecimal.valueOf(workQuantity.longValue() * bomMaterial.getQuantity()))
                            .divide(BigDecimal.valueOf(material.getQuantity()), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 엔티티를 DTO로 변환
    private ProductionOrderDTO convertToDTO(ProductionOrder productionOrder) {
        return ProductionOrderDTO.builder()
                .id(productionOrder.getId())
                .name(productionOrder.getName())
                .remarks(productionOrder.getRemarks())
                .confirmed(productionOrder.getConfirmed())
                .closed(productionOrder.getClosed())
                .startDateTime(productionOrder.getStartDateTime())
                .endDateTime(productionOrder.getEndDateTime())
                .productionQuantity(productionOrder.getProductionQuantity())
                .actualStartDateTime(productionOrder.getActualStartDateTime())
                .actualEndDateTime(productionOrder.getActualEndDateTime())
                .actualProductionQuantity(productionOrder.getActualProductionQuantity())
                .workers(productionOrder.getWorkers())
                .actualWorkers(productionOrder.getActualWorkers())
                .mpsId(productionOrder.getMps() != null ? productionOrder.getMps().getId() : null)
                .processDetailsId(productionOrder.getProcessDetails() != null ? productionOrder.getProcessDetails().getId() : null)
                .build();
    }

    // DTO를 엔티티로 변환
    private ProductionOrder convertToEntity(ProductionOrderDTO productionOrderDTO) {
        Mps mps = mpsRepository.findById(productionOrderDTO.getMpsId()).orElseThrow(() -> new EntityNotFoundException("MPS를 찾을 수 없습니다."));
        ProcessDetails processDetails = processDetailsRepository.findById(productionOrderDTO.getProcessDetailsId()).orElseThrow(() -> new EntityNotFoundException("공정 세부를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByProcessDetailsId(processDetails.getId())
                .stream()
                .findFirst().orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));

        return ProductionOrder.builder()
                .name(productionOrderDTO.getName())
                .remarks(productionOrderDTO.getRemarks())
                .confirmed(productionOrderDTO.getConfirmed())
                .closed(productionOrderDTO.getClosed())
                .startDateTime(productionOrderDTO.getStartDateTime())
                .endDateTime(productionOrderDTO.getEndDateTime())
                .productionQuantity(productionOrderDTO.getProductionQuantity())
                .actualStartDateTime(productionOrderDTO.getActualStartDateTime())
                .actualEndDateTime(productionOrderDTO.getActualEndDateTime())
                .actualProductionQuantity(productionOrderDTO.getActualProductionQuantity())
                .workers(productionOrderDTO.getWorkers())
                .actualWorkers(productionOrderDTO.getActualWorkers())
                .mps(mps)
                .processDetails(processDetails)
                .workcenter(workcenter)
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
