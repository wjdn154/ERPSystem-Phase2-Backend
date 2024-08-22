package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting;

//import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
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
//import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionRoutingServiceImpl implements ProductionRoutingService {

    private final ProductGroupRepository productGroupRepository;
    private final ProductionRoutingRepository productionRoutingRepository;
    private final RoutingStepRepository routingStepRepository;
    private final ProcessDetailsRepository processDetailsRepository;


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

    // ProductionRouting DTO 변환 메서드
    public ProductionRoutingDTO convertToDTO(ProductionRouting productionRouting) {

//        List<RoutingStepDTO> routingStepDTOList = productionRouting.getRoutingSteps().stream()
//                .map(this::convertRoutingStepToDTO) // 각 RoutingStep을 RoutingStepDTO로 변환
//                .collect(Collectors.toList());
//
//        List<ProductDetailDto> productDTOList = productionRouting.getProducts().stream()
//                .map(this::convertProductToDTO) // 각 Product를 ProductDetailDto로 변환
//                .collect(Collectors.toList());

        return ProductionRoutingDTO.builder()
                .id(productionRouting.getId())
                .code(productionRouting.getCode())
                .name(productionRouting.getName())
                .description(productionRouting.getDescription())
                .isStandard(productionRouting.isStandard())
                .isActive(productionRouting.isActive())
//                .routingStepDTOList(routingStepDTOList) // 변환된 RoutingStep 목록 추가
//                .products(productDTOList) // 변환된 Product 목록 추가
                .build();
    }


//    // Product Entity -> DTO
//    public ProductDetailDto convertToDTO(Product product) {
//        return ProductDetailDto.builder()
//                .code(product.getCode()) // 품목 코드
//                .name(product.getName()) // 품목명
//                .productGroupName(product.getProductGroup().getName()) // 폼목 그룹 이름
//                .standard(product.getStandard()) // 규격
//                .unit(product.getUnit()) // 단위
//                .purchasePrice(product.getPurchasePrice()) // 입고 단가
//                .salesPrice(product.getSalesPrice()) // 출고 단가
//                .productType(product.getProductType()) // 품목 구분
//                .productionRouting(product.getProductionRouting()) // 생산라우팅
//                .build();
//    }


    //
//    public RoutingStepDTO convertToDTO(RoutingStep routingStep) {
//        return RoutingStepDTO.builder()
//                .routingId(routingStep.getId().getRoutingId()) // Routing ID
//                .processId(routingStep.getProcess().getId()) // Process ID
//                .stepOrder(routingStep.getStepOrder()) // 공정 순서
//                .processDetailsDTO(convertProcessDetailsToDTO(routingStep.getProcess())) // 공정 정보 DTO로 변환
//                .productionRoutingDTO(convertProductionRoutingToDTO(routingStep.getProductionRouting())) // Routing 정보 DTO로 변환
//                .build();
//    }


    // ProcessDetails Entity -> DTO
    private ProcessDetailsDTO convertProcessDetailsToDTO(ProcessDetails processDetails) {
        return ProcessDetailsDTO.builder()
                .id(processDetails.getId())
                .code(processDetails.getCode())
                .name(processDetails.getName())
                .description(processDetails.getDescription())
                .isOutsourced(processDetails.getIsOutsourced())
                .duration(processDetails.getDuration())
                .cost(processDetails.getCost())
                .defectRate(processDetails.getDefectRate())
                .isUsed(processDetails.getIsUsed())
                .build();
    }

    // ProductionRouting DTO -> Entity
    public ProductionRouting convertToEntity(ProductionRoutingDTO dto) {
        ProductionRouting productionRouting = ProductionRouting.builder()
                .code(dto.getCode()) // Routing 지정코드
                .name(dto.getName()) // Routing 이름
                .description(dto.getDescription()) // Routing 설명
                .isStandard(dto.isStandard()) // 표준 여부
                .isActive(dto.isActive()) // 사용 여부
                .build();

//        // DTO의 RoutingStepDTO 리스트를 RoutingStep 엔티티 리스트로 변환
//        List<RoutingStep> routingSteps = dto.getRoutingStepDTOList().stream()
//                .map(this::convertDTOToRoutingStep) // 각 RoutingStepDTO를 RoutingStep으로 변환
//                .collect(Collectors.toList());
//
//        // DTO의 ProductDetailDto 리스트를 Product 엔티티 리스트로 변환
//        List<Product> products = dto.getProducts().stream()
//                .map(this::convertDTOToProduct) // 각 ProductDetailDto를 Product로 변환
//                .collect(Collectors.toList());
//
//        // 변환된 엔티티 리스트들을 ProductionRouting 엔티티에 설정
//        productionRouting.setRoutingSteps(routingSteps); // 변환된 RoutingStep 목록 설정
//        productionRouting.setProducts(products); // 변환된 Product 목록 설정

        return productionRouting;
    }
//
//    // RoutingStepDTO -> Entity
//    private RoutingStep convertDTOToRoutingStep(RoutingStepDTO dto) {
//        ProcessDetails processDetails = processDetailsRepository.findById(dto.getProcessId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid process ID: " + dto.getProcessId()));
//
//        return RoutingStep.builder()
//                .id(new RoutingStepId(dto.getRoutingId(), dto.getProcessId())) // 복합 키 설정
//                .process(processDetails) // ProcessDetails 엔티티 설정
//                .stepOrder(dto.getStepOrder()) // 공정 순서 설정
//                .productionRouting(productionRoutingRepository.findById(dto.getRoutingId())
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid routing ID: " + dto.getRoutingId()))) // ProductionRouting 설정
//                .build();
//    }
//
//    // ProductDetailDto -> Entity
//    private Product convertDTOToProduct(ProductDetailDto dto) {
//        // Product 엔티티는 적절한 ProductRepository를 사용해 변환
//        return Product.builder()
//                .id(dto.getId()) // Product ID 설정
//                .name(dto.getName()) // Product 이름 설정
//                .code(dto.getCode()) // Product 코드 설정
//                .productGroup(productGroupRepository.findByName(dto.getProductGroupName())
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid product group name: " + dto.getProductGroupName()))) // ProductGroup 설정
//                .standard(dto.getStandard()) // 규격 설정
//                .unit(dto.getUnit()) // 단위 설정
//                .purchasePrice(dto.getPurchasePrice()) // 입고 단가 설정
//                .salesPrice(dto.getSalesPrice()) // 출고 단가 설정
//                .productType(dto.getProductType()) // 품목 구분 설정
//                .productionRouting(productionRoutingRepository.findById(dto.getProductionRouting().getId())
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid routing ID: " + dto.getProductionRouting().getId()))) // ProductionRouting 설정
//                .build();
//    }

}
