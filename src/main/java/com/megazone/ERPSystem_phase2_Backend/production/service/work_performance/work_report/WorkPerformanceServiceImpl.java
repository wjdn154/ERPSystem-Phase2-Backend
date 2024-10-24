package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.work_report.WorkPerformanceRepository;

import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class WorkPerformanceServiceImpl implements WorkPerformanceService{

    private final WorkPerformanceRepository workPerformanceRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final ProductRepository productRepository;

    private final ProductionOrderService productionOrderService;

    //모든 작업 실적 리스트 조회
    @Override
    public List<WorkPerformanceListDTO> findAllWorkPerformance() {

        return workPerformanceRepository.findAllByOrderByIdDesc().stream()
                .map(workPerformance -> new WorkPerformanceListDTO(
                        workPerformance.getId(),
                        workPerformance.getQuantity() != null ? workPerformance.getQuantity() : BigDecimal.ZERO,
                        workPerformance.getProductionOrder().getId(),
                        workPerformance.getProductionOrder().getName(),
                        workPerformance.getProduct().getCode(),
                        workPerformance.getProduct().getName()
                        )
                ).collect(Collectors.toList());
    }

    //작업 실적 리스트 상세 조회
    @Override
    public Optional<WorkPerformanceDetailDTO> findWorkPerformanceById(Long id) {

        WorkPerformance workPerformance = workPerformanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업 실적 아이디를 조회할 수 없습니다."));

        //엔티티를 dto로 변환
        WorkPerformanceDetailDTO workPerformanceDetailDTO = workPerformanceToDTO(workPerformance);
        return Optional.of(workPerformanceDetailDTO);
    }

    //작업 실적 상세 정보 등록
    @Override
    public Optional<WorkPerformanceDetailDTO> createWorkPerformance(WorkPerformanceDetailDTO dto) {

        //dto를 엔티티로 변환
        WorkPerformance workPerformance = workPerformanceToEntity(dto);

        WorkPerformance savedWorkPerformance = workPerformanceRepository.save(workPerformance);
        //엔티티를 dto로 변환
        WorkPerformanceDetailDTO workPerformanceDetailDTO = workPerformanceToDTO(savedWorkPerformance);

        return Optional.of(workPerformanceDetailDTO);
    }

    //작업 실적 상세 정보 수정
    @Override
    public Optional<WorkPerformanceDetailDTO> updateWorkPerformance(Long id, WorkPerformanceDetailDTO dto) {

        //아이디 존재 확인
        WorkPerformance workPerformance = workPerformanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업 실적 아이디를 조회할 수 없습니다."));

        //dto를 엔티티로 변환
        WorkPerformance workPerformanceEntity = updateWorkPerformanceToEntity(workPerformance, dto);

        WorkPerformance updatedWorkPerformance = workPerformanceRepository.save(workPerformanceEntity);

        //엔티티를 dto로 변환
        WorkPerformanceDetailDTO workPerformanceDetailDTO = workPerformanceToDTO(updatedWorkPerformance);

        return Optional.of(workPerformanceDetailDTO);
    }



    // 엔티티를 WorkPerformanceDetailDTO로 변환
    private WorkPerformanceDetailDTO workPerformanceToDTO(WorkPerformance workPerformance) {

        WorkPerformanceDetailDTO dto = WorkPerformanceDetailDTO.builder()
                .id(workPerformance.getId())
                .actualQuantity(workPerformance.getQuantity() != null ? workPerformance.getQuantity() : BigDecimal.ZERO)  // HEAD 기준
                .productionOrderId(workPerformance.getProductionOrder().getId())
                .productionOrderName(workPerformance.getProductionOrder().getName())
                .productCode(workPerformance.getProduct().getCode())
                .productName(workPerformance.getProduct().getName())
                .build();

        return dto;
    }

    // 등록 dto를 엔티티로 변환
    private WorkPerformance workPerformanceToEntity(WorkPerformanceDetailDTO dto) {

        ProductionOrder productionOrder = productionOrderRepository.findById(dto.getProductionOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업지시 아이디가 없습니다."));

        Product product = productRepository.findByCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 품목 코드가 존재하지 않습니다."));

        WorkPerformance workPerformance = WorkPerformance.builder()
                .id(dto.getId())
                .quantity(dto.getActualQuantity() != null ? dto.getActualQuantity() : BigDecimal.ZERO)  // HEAD 기준
                .productionOrder(productionOrder)
                .product(product)
                .build();

        return workPerformance;
    }

    //수정 엔티티를 dto로 변환
    private WorkPerformance updateWorkPerformanceToEntity(WorkPerformance workPerformance , WorkPerformanceDetailDTO dto) {

//        WorkDailyReport workDailyReport = workDailyReportRepository.findByWorkDailyReportCode(dto.getWorkDailyReportCode())
//                .orElseThrow(() -> new IllegalArgumentException("해당하는 일별 보고서 코드가 존재하지 않습니다."));

        ProductionOrder productionOrder = productionOrderRepository.findById(dto.getProductionOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작업지시 아이디가 없습니다."));

        Product product = productRepository.findByCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 품목 코드가 존재하지 않습니다."));

        workPerformance.setId(workPerformance.getId());
        workPerformance.setQuantity(dto.getActualQuantity() != null ? dto.getActualQuantity() : BigDecimal.ZERO);
        workPerformance.setProductionOrder(productionOrder);
        workPerformance.setProduct(product);

        return workPerformance;


    }

    /**
     * WorkPerformance와 연관된 ProductionOrder ID 조회
     */
    private Long getProductionOrderIdByWorkPerformance(Long workPerformanceId) {
        return workPerformanceRepository.findProductionOrderIdByWorkPerformanceId(workPerformanceId);
    }
}
