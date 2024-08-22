package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.RoutingStep.RoutingStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductionRoutingServiceImpl implements ProductionRoutingService {

    private final ProductionRoutingRepository productionRoutingRepository;
    private final RoutingStepRepository routingStepRepository;
    private final ProcessDetailsRepository processDetailsRepository;

    // DTO 변환 메서드
    public ProductionRoutingDTO convertToDTO(ProductionRouting productionRouting) {
        return ProductionRoutingDTO.builder()
                .id(productionRouting.getId())
                .code(productionRouting.getCode())
                .name(productionRouting.getName())
                .description(productionRouting.getDescription())
                .isStandard(productionRouting.isStandard())
                .isActive(productionRouting.isActive())
                .build();
    }

    // 엔티티 변환
    public ProductionRouting convertToEntity(ProductionRoutingDTO dto) {
        return ProductionRouting.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .isStandard(dto.isStandard())
                .isActive(dto.isActive())
                .build();
    }

    @Transactional
    public ProductionRouting createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO, List<RoutingStepDTO> stepDTOs) {
        // 1. 고유코드 중복 확인
        if (productionRoutingRepository.existsByCode(routingDTO.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다: " + routingDTO.getCode());
        }

        // 2. Routing을 먼저 저장 (DTO를 엔티티로 변환 후 저장)
        ProductionRouting routing = convertToEntity(routingDTO);
        ProductionRouting savedRouting = productionRoutingRepository.save(routing);

        // 3. 순서 검증 로직 추가
        validateStepOrder(stepDTOs);

//        // 4. Stream API를 사용하여 각 RoutingStepDTO를 RoutingStep 엔티티로 변환하고 순서 설정 후 저장
//        stepDTOs.stream()
//                .map(stepDTO -> {
//                    RoutingStep routingStep = new RoutingStep();
//                    routingStep.setProductionRouting(savedRouting);
//                    routingStep.setProcess(new ProcessDetails(stepDTO.getProcessId()));
//                    routingStep.setStepOrder(stepDTO.getStepOrder());
//                    return routingStep;
//                })
//                .forEach(routingStepRepository::save);

        // 4. Stream API를 사용하여 각 RoutingStepDTO를 RoutingStep 엔티티로 변환하고 순서 설정 후 저장
        stepDTOs.stream()
                .map(stepDTO -> {
                    RoutingStep routingStep = new RoutingStep();

                    // RoutingStepId 생성
                    RoutingStepId id = new RoutingStepId(savedRouting.getId(), stepDTO.getProcessId());
                    routingStep.setId(id);

                    // ProcessDetails 조회
                    ProcessDetails processDetails = processDetailsRepository.findById(stepDTO.getProcessId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Process ID: " + stepDTO.getProcessId()));

                    // 다른 필드 설정
                    routingStep.setProductionRouting(savedRouting); // ProductionRouting 객체 사용
                    routingStep.setProcess(processDetails); // 조회한 ProcessDetails 객체 사용
                    routingStep.setStepOrder(stepDTO.getStepOrder());

                    return routingStep;
                })
                .forEach(routingStepRepository::save);

        return savedRouting;
    }

    private void validateStepOrder(List<RoutingStepDTO> stepDTOs) {
        Set<Long> stepOrders = new HashSet<>();
        for (RoutingStepDTO stepDTO : stepDTOs) {
            if (!stepOrders.add(stepDTO.getStepOrder())) {
                throw new IllegalArgumentException("중복된 stepOrder 값이 있습니다: " + stepDTO.getStepOrder());
            }
        }

        long maxOrder = stepDTOs.stream()
                .mapToLong(RoutingStepDTO::getStepOrder)
                .max()
                .orElse(0);

        if (stepOrders.size() != maxOrder) {
            throw new IllegalArgumentException("연속된 stepOrder 값이 아닙니다.");
        }
    }

    // ProductionRouting 업데이트 로직
    public Optional<ProductionRouting> updateProductionRouting(Long id, ProductionRoutingDTO productionRoutingDTO) {
        return productionRoutingRepository.findById(id)
                .map(existingRouting -> {
                    existingRouting.setCode(productionRoutingDTO.getCode());
                    existingRouting.setName(productionRoutingDTO.getName());
                    existingRouting.setDescription(productionRoutingDTO.getDescription());
                    existingRouting.setStandard(productionRoutingDTO.isStandard());
                    existingRouting.setActive(productionRoutingDTO.isActive());
                    return productionRoutingRepository.save(existingRouting);
                });
    }


    // ProductionRouting 삭제 로직
    public Optional<ProductionRouting> deleteProductionRouting(Long id) {
        return productionRoutingRepository.findById(id)
                .filter(existingRouting -> !existingRouting.isActive()) // isActive가 false일 때만 삭제
                .map(existingRouting -> {
                    productionRoutingRepository.delete(existingRouting);
                    return existingRouting; // 삭제된 엔티티를 반환
                });
    }

}