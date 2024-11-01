package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MpsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.searchMpsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request.ProductionRequestsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.crp.CrpService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp.MrpService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.RoutingStepDetailDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class MpsServiceImpl implements MpsService {

    private final MpsRepository mpsRepository;
    private final ProductRepository productRepository;
    private final ProductionRequestsRepository productionRequestsRepository;
    private final ProductionOrderRepository productionOrderRepository;
//    private final MrpService mrpService;
//    private final CrpService crpService;
    private final ProductionOrderService productionOrderService;
    private final WorkcenterRepository workcenterRepository;
    private final WorkerRepository workerRepository;


    /**
     * 자동생성
     * @param mpsDto
     * @return
     */
    public Mps createMps(MpsDTO mpsDto) {
        // 1. 연관된 ProductionRequest 조회
        ProductionRequest productionRequest = productionRequestsRepository.findById(mpsDto.getProductionRequestId())
                .orElseThrow(() -> new EntityNotFoundException("해당 생산 의뢰를 찾을 수 없습니다."));

        // 2. 연관된 Product 조회
        Product product = productRepository.findById(mpsDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 제품을 찾을 수 없습니다."));

        // 3. MPS 엔티티 생성
        Mps mps = Mps.builder()
                .name("MPS - " + productionRequest.getName())
                .productionRequest(productionRequest)
                .product(product)
                .quantity(mpsDto.getQuantity())
                .orders(productionRequest.getSalesOrder())
                .startDate(mpsDto.getStartDate())
                .endDate(mpsDto.getEndDate())
                .status(mpsDto.getStatus() != null ? mpsDto.getStatus() : "계획") // 기본값 설정
                .remarks(mpsDto.getRemarks())
                .build();

        // 4. MPS 저장
        Mps savedMps = mpsRepository.save(mps);

        // 5. ProductionOrder 처리 (선택적으로 추가)
        if (mpsDto.getProductionOrderIds() != null && !mpsDto.getProductionOrderIds().isEmpty()) {
            assignProductionOrdersToMps(savedMps, mpsDto.getProductionOrderIds());
        }

        return savedMps;
    }

    private void assignProductionOrdersToMps(Mps mps, List<Long> productionOrderIds) {
        // ProductionOrder를 조회하고 MPS에 할당
        List<ProductionOrder> productionOrders = productionOrderRepository.findAllById(productionOrderIds);
        productionOrders.forEach(order -> order.setMps(mps));
        productionOrderRepository.saveAll(productionOrders);
    }


    /**
     * MPS 상태 변경에 따른 ProductionRequest 상태 자동 전환
     */
    public void updateMpsStatus(Long mpsId, String newStatus) {
        Mps mps = mpsRepository.findById(mpsId)
                .orElseThrow(() -> new EntityNotFoundException("MPS를 찾을 수 없습니다."));

        mps.setStatus(newStatus);
        mpsRepository.save(mps);

        // MPS 상태에 따라 ProductionRequest 상태 자동 전환
        updateProductionRequestProgress(mps);
    }

    @Override
    public MpsDTO confirmMps(Long mpsId) {

        Mps mps = mpsRepository.findById(mpsId)
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다."));

        mps.setStatus("확정");
        Mps updatedMps = mpsRepository.save(mps); // 업데이트된 MPS 저장

//        // MPS 상태에 따라 ProductionRequest의 상태 업데이트
//        updateProductionRequestProgress(mps);
        MpsDTO mpsDto = convertToDto(updatedMps);
        return mpsDto;
    }

    /**
     * ProductionOrder 마감 여부에 따른 MPS 상태 업데이트
     */
    @Override

    public void updateMpsStatusBasedOnOrders(Long mpsId) {
        Mps mps = mpsRepository.findById(mpsId)
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다."));

        // 모든 ProductionOrder가 마감되었는지 확인
        boolean allOrdersClosed = productionOrderRepository.findByMpsId(mpsId).stream()
                .allMatch(ProductionOrder::getClosed);

        // MPS 상태 업데이트
        String newStatus = allOrdersClosed ? "완료" : "진행 중";
        mps.setStatus(newStatus);

        mpsRepository.save(mps);

        // MPS 상태에 따라 ProductionRequest의 상태 업데이트
        updateProductionRequestProgress(mps);
    }

    /**
     * MPS 상태에 따라 ProductionRequest의 ProgressType 전환
     */
    private void updateProductionRequestProgress(Mps mps) {

        ProductionRequest request = mps.getProductionRequest();
        if (request == null) {
            return; // 또는 예외를 던질 수 있음
        }

        ProgressType newProgressType = switch (mps.getStatus()) {
            case "계획" -> ProgressType.CREATED;
            case "확정" -> ProgressType.NOT_STARTED;
            case "진행 중" -> ProgressType.IN_PROGRESS;
            case "완료" -> ProgressType.COMPLETED;
            default -> throw new IllegalArgumentException("알 수 없는 MPS 상태: " + mps.getStatus());
        };

        request.setProgressType(newProgressType);
        productionRequestsRepository.save(request);
    }



    @Override
    public MpsDTO saveMps(MpsDTO mpsDto) {
        if (mpsDto == null) {
            throw new IllegalArgumentException("MPS 정보가 입력되지 않았습니다.");
        }
        if (mpsDto.getProductId() == null) {
            throw new IllegalArgumentException("제품 정보가 필요합니다.");
        }
        if (mpsDto.getQuantity() == null || mpsDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("생산 수량이 올바르지 않습니다.");
        }
        if (mpsDto.getStartDate() == null || mpsDto.getEndDate() == null) {
            throw new IllegalArgumentException("생산 시작일과 종료일을 입력해야 합니다.");
        }
        if (mpsDto.getEndDate().isBefore(mpsDto.getStartDate())) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }

        // 제품 조회
        Product product = productRepository.findById(mpsDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("해당 품목을 찾을 수 없습니다."));

        // MPS 저장
        Mps mps = convertToEntity(mpsDto);
        Mps savedMps = mpsRepository.save(mps);

        // MPS 저장 후 자동으로 ProductionOrder 생성
        productionOrderService.createOrdersFromMps(savedMps);

        // MPS 상태를 IN_PROGRESS로 전환
        updateMpsStatus(savedMps.getId(), "진행 중");

        return convertToDto(savedMps);
    }

    @Override
    public List<searchMpsDTO> searchMps(LocalDate date) {
        List<Mps> mpsList = mpsRepository.searchMps(date);

        return mpsList.stream()
                .map(mps -> {
                    List<RoutingStepDetailDTO> routingStepDetailDTOs = new ArrayList<>();

                    if (mps.getProduct() != null && mps.getProduct().getProcessRouting() != null) {
                        routingStepDetailDTOs = mps.getProduct().getProcessRouting().getRoutingSteps().stream()
                                .map(step -> {
                                    ProcessDetailsDTO processDetailsDTO = step.getProcessDetails() != null
                                            ? ProcessDetailsDTO.builder()
                                            .id(step.getProcessDetails().getId())
                                            .name(step.getProcessDetails().getName())
                                            .workcenterDTOList(workcenterRepository.findByProcessDetailsId(step.getProcessDetails().getId())
                                                    .stream()
                                                    .map(workcenter -> WorkcenterDTO.builder()
                                                            .id(workcenter.getId())
                                                            .name(workcenter.getName())
                                                            .code(workcenter.getCode())
                                                            .workcenterType(workcenter.getWorkcenterType())
                                                            .isActive(workcenter.getIsActive())
                                                            .description(workcenter.getDescription())
                                                            .factoryCode(workcenter.getFactory().getCode())
                                                            .factoryName(workcenter.getFactory().getName())
                                                            .build())
                                                    .collect(Collectors.toList()))
                                            .build()
                                            : null;

                                    return RoutingStepDetailDTO.builder()
                                            .id(step.getId())
                                            .stepOrder(step.getStepOrder())
                                            .processDetails(processDetailsDTO)
                                            .build();
                                })
                                .collect(Collectors.toList());
                    }

                    return searchMpsDTO.builder()
                            .id(mps.getId())
                            .name(mps.getName())
                            .status(mps.getStatus())
                            .productName(mps.getProduct() != null ? mps.getProduct().getName() : null)
                            .quantity(mps.getQuantity())
                            .planDate(mps.getPlanDate())
                            .startDate(mps.getStartDate())
                            .endDate(mps.getEndDate())
                            .routingSteps(routingStepDetailDTOs)
                            .remarks(mps.getRemarks())
                            .productionRequestId(mps.getProductionRequest() != null ? mps.getProductionRequest().getId() : null)
                            .saleId(mps.getSale() != null ? mps.getSale().getId() : null)
                            .workers(workerRepository.findAll().stream()
                                    .map(worker -> WorkerDTO.builder()
                                            .name(worker.getEmployee().getLastName() + worker.getEmployee().getFirstName())
                                            .position(worker.getEmployee().getPosition().getPositionName())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public MpsDTO getMpsById(Long id) {
        Mps mps = mpsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다. :" + id));

        MpsDTO mpsDto = convertToDto(mps);

//        Hibernate.initialize(mps.getProduct()); // Lazy 로딩된 연관 엔티티를 초기화
//        Hibernate.initialize(mps.getProductionRequest());
        return mpsDto;
    }

    @Override
    public MpsDTO updateMps(Long id, MpsDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("MPS 정보가 입력되지 않았습니다.");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("생산 수량이 올바르지 않습니다.");
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null &&
                dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }

        Mps existingMps = mpsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다. :" + id));

        // 업데이트할 필드만 업데이트
        if (dto.getName() != null) {
            existingMps.setName(dto.getName());
        }
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 품목을 찾을 수 없습니다."));
            existingMps.setProduct(product);
        }
        if (dto.getQuantity() != null) {
            existingMps.setQuantity(dto.getQuantity());
        }
        if (dto.getStartDate() != null) {
            existingMps.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            existingMps.setEndDate(dto.getEndDate());
        }
        if (dto.getStatus() != null) {
            existingMps.setStatus(dto.getStatus());
        }
        if (dto.getRemarks() != null) {
            existingMps.setRemarks(dto.getRemarks());
        }

        Mps updatedMps = mpsRepository.save(existingMps);

        return convertToDto(updatedMps);
    }

    @Override
    public String deleteMps(Long id) {
        try {
            // MPS 조회
            Mps existingMps = mpsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다: " + id));

            // 진행 중인 MPS는 삭제 불가
            if ("진행 중".equals(existingMps.getStatus())) {
                throw new IllegalStateException("진행 중인 MPS는 삭제할 수 없습니다.");
            }

            // MPS 삭제
            mpsRepository.delete(existingMps);

            // 성공 메시지 반환
            return "MPS가 성공적으로 삭제되었습니다.";
        } catch (EntityNotFoundException e) {
            // 엔티티를 찾지 못한 경우 예외 처리
            return "삭제 실패: " + e.getMessage();
        } catch (Exception e) {
            // 그 외의 다른 예외 처리
            return "삭제 중 오류가 발생했습니다: " + e.getMessage();
        }
    }


// ================= convert methods ===============

    private MpsDTO convertToDto(Mps mps) {

        MpsDTO mpsDto = MpsDTO.builder()
                .id(mps.getId())
                .name(mps.getName())
                .planDate(mps.getPlanDate())
                .startDate(mps.getStartDate())
                .endDate(mps.getEndDate())
                .status(mps.getStatus())
                .productId(mps.getProduct() != null ? mps.getProduct().getId() : null)
                .quantity(mps.getQuantity())
                .remarks(mps.getRemarks())
                .productionRequestId(mps.getProductionRequest() != null ? mps.getProductionRequest().getId() : null)
                .ordersId(mps.getOrders() != null ? mps.getOrders().getId() : null)
                .saleId(mps.getSale() != null ? mps.getSale().getId() : null)
                .productionOrderIds(
                        productionOrderRepository.findByMpsId(mps.getId()).stream()
                                .map(ProductionOrder::getId)
                                .collect(Collectors.toList())
                )
                .build();

        return mpsDto;
    }

    private Mps convertToEntity(MpsDTO dto) {
            // Product 조회
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 제품을 찾을 수 없습니다."));

            // ProductionRequest 조회
            ProductionRequest productionRequest = productionRequestsRepository.findById(dto.getProductionRequestId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 생산 의뢰를 찾을 수 없습니다."));

            // ProductionOrder 리스트 생성 (선택적으로 연결)
            List<ProductionOrder> productionOrders = dto.getProductionOrderIds() != null ?
                    productionOrderRepository.findAllById(dto.getProductionOrderIds()) : List.of();

        return Mps.builder()
                .name(dto.getName())
                .planDate(dto.getPlanDate() != null ? dto.getPlanDate() : LocalDate.now())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus() != null ? dto.getStatus() : "계획") // 기본 상태 설정
                .product(product)
                .quantity(dto.getQuantity())
                .remarks(dto.getRemarks())
                .productionRequest(productionRequest)
                .build();
    }
}
