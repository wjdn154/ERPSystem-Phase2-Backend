package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessRouting;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting.ProcessRoutingRepository;

import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.RoutingStep.RoutingStepRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcessRoutingServiceImpl implements ProcessRoutingService {

    private final ProcessRoutingRepository processRoutingRepository;
    private final ProcessDetailsRepository processDetailsRepository;
    private final ProductRepository productRepository;
    private final RoutingStepRepository routingStepRepository;
//    private final ProductGroupRepository productGroupRepository;

    /**
     * 생산라우팅 등록 -> 고유코드 중복확인, 생산공정 및 제품 조회 후 등록하여 각 공정단계 등록 시 RoutingStep 테이블 자동등록
     *
     * @param routingDTO
     * @return ProcessRouting
     */
    public ProcessRoutingDTO createProcessRoutingWithSteps(ProcessRoutingDTO routingDTO) {
        // 1. 고유코드 중복 확인
        if (processRoutingRepository.existsByCode(routingDTO.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다: " + routingDTO.getCode());
        }

        // 2. Routing을 먼저 엔티티로 변환
        ProcessRouting routing = convertToEntity(routingDTO);

        // ProcessRouting을 먼저 저장하여 ID를 생성
        routing = processRoutingRepository.save(routing);

        // 3. 순서 검증 로직 호출
        validateStepOrder(routingDTO.getRoutingSteps());

        // 4. 공정단계에서 생산공정, 라우팅 연결하는 RoutingSteps 생성
        List<RoutingStep> routingSteps = createRoutingSteps(routingDTO.getRoutingSteps(), routing);
        routing.setRoutingSteps(routingSteps);

        // 5. 최소 두 개 이상의 공정이 입력되었는지 확인
        if (routingSteps.size() < 2) {
            throw new IllegalArgumentException("라우팅에 최소 두 개 이상의 공정이 필요합니다.");
        }

        // 6. Product 변환 및 설정
        List<Product> products = routingDTO.getProducts().stream()
                .map(this::convertProductDtoToEntity) // 수정된 부분
                .collect(Collectors.toList());
        routing.setProducts(products);


        // 7. 라우팅 및 연관 엔티티 저장
        ProcessRouting newRouting = processRoutingRepository.save(routing);

        return convertToDTO(newRouting);
    }

    private Product convertProductDtoToEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .code(productDto.getCode())
                .name(productDto.getName())
                .build();
    }

    /**
     * stepOrder 순서검증 메서드 : 빈값, 중복, 비연속
     * @param stepDTOs
     */
    private void validateStepOrder(List<RoutingStepDTO> stepDTOs) {
        if (stepDTOs == null || stepDTOs.isEmpty()) {
            throw new IllegalArgumentException("RoutingStep 목록이 비어있습니다.");
        }

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

    /**
     * create, update 에서 필요한 RoutingStep 등록 공통 메서드
     *
     * RoutingStepDTO 리스트와 ProcessRouting 엔티티를 기반으로
     * 각 RoutingStep 엔티티를 생성하고 리스트로 반환
     * 생성된 RoutingStep은 주어진 ProcessRouting에 연결
     *
     * @param stepDTOList  생성 또는 업데이트할 RoutingStepDTO의 리스트
     * @param routing      RoutingStep이 연결될 ProcessRouting 엔티티
     * @return 생성된 RoutingStep 엔티티의 리스트
     */

    private List<RoutingStep> createRoutingSteps(List<RoutingStepDTO> stepDTOList, ProcessRouting routing) {
        return stepDTOList.stream()
                .map(stepDTO -> {
                    Long processId = stepDTO.getId().getProcessId(); // id에서 processId 추출
                    RoutingStepId id = new RoutingStepId(routing.getId(), processId);

                    ProcessDetails processDetails = processDetailsRepository.findById(processId)
                            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 공정 ID입니다: " + processId));

                    return RoutingStep.builder()
                            .id(id)
                            .processRouting(routing)
                            .processDetails(processDetails)
                            .stepOrder(stepDTO.getStepOrder())
                            .build();
                })
                .collect(Collectors.toList());



    }

    /**
     * @return 검색 keyword를 포함한 고유코드 혹은 이름을 가진 생산공정, 제품들 검색결과 list 반환
     */

    @Override
    public List<ProcessDetailsDTO> searchProcessDetails() {
        List<ProcessDetails> processDetailsList = processDetailsRepository.findAll();
        return processDetailsList.stream()
                .map(this::convertProcessDetailsToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        List<Product> productList = productRepository.findByCodeContainingOrNameContaining(keyword, keyword);
        if (productList.isEmpty()) {
            throw new IllegalArgumentException("해당 키워드를 포함한 품목을 찾을 수 없습니다. 품목관리에서 새 품목을 등록하거나 정확한 키워드로 조회해 주세요.");
        }
        return productList.stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    /**
     * RoutingStep 등록을 위해 ID만 따로
     * 주어진 코드 또는 이름으로 ProcessDetails를 조회하여 그 ID를 반환
     * @param processDetailsDTO 조회할 ProcessDetails의 정보를 담은 DTO
     * @return ProcessDetails의 ID
     */
    private Long getProcessIdByCodeOrName(ProcessDetailsDTO processDetailsDTO) {
        if (processDetailsDTO == null) {
            throw new IllegalArgumentException("생산공정 데이터가 비어 있습니다.");
        }
        // 우선 코드로 조회, 없으면 이름으로 조회
        return processDetailsRepository.findByCode(processDetailsDTO.getCode())
                .map(ProcessDetails::getId)
                .or(() -> processDetailsRepository.findByName(processDetailsDTO.getName()).map(ProcessDetails::getId))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 생산공정의 코드나 이름을 찾을 수 없습니다: "
                        + processDetailsDTO.getCode() + ", " + processDetailsDTO.getName()));
    }


    public ProcessDetailsDTO getProcessDetailsById(Long id) {
        return processDetailsRepository.findById(id)
                .map(this::convertProcessDetailsToDTO)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 생산공정을 찾을 수 없습니다."));
    }

    @Override
    public ProcessRoutingDetailDTO getProcessRoutingById(Long id) {

        ProcessRouting processRouting = processRoutingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공정경로를 찾을 수 없습니다: " + id));

        List<RoutingStep> allByProcessRoutingId = routingStepRepository.findAllByProcessRoutingIdWithDetails(id);

        List<ProcessDetails> processDetailsList = allByProcessRoutingId.stream()
                .map(RoutingStep::getProcessDetails)
                .collect(Collectors.toList());

        ProcessRoutingDTO processRoutingDTO = convertToDTO(processRouting);

        List<ProductDto> productDTOList = processRouting.getProducts().stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getCode(),
                        product.getName(),
                        product.getProductGroup().getName(),
                        product.getStandard(),
                        product.getPurchasePrice().doubleValue(),
                        product.getSalesPrice().doubleValue(),
                        product.getProductType(),
                        product.getProcessRouting().getName(),
                        product.isActive()
                )).collect(Collectors.toList());

        ProcessRoutingDetailDTO processRoutingDetailDTO = new ProcessRoutingDetailDTO();
        processRoutingDetailDTO.setId(processRoutingDTO.getId());
        processRoutingDetailDTO.setCode(processRoutingDTO.getCode());
        processRoutingDetailDTO.setName(processRoutingDTO.getName());
        processRoutingDetailDTO.setDescription(processRoutingDTO.getDescription());
        processRoutingDetailDTO.setStandard(processRoutingDTO.isStandard());
        processRoutingDetailDTO.setActive(processRoutingDTO.isActive());
        processRoutingDetailDTO.setProcessDetails(processDetailsList);
        processRoutingDetailDTO.setProducts(productDTOList);

        return processRoutingDetailDTO;
    }

    /**
     * 주어진 코드 또는 이름으로 Product를 조회하여 그 엔티티를 반환
     * @param productDetailDto 조회할 Product의 정보를 담은 DTO
     * @return Product 엔티티
     */
    private Product getProductByCodeOrName(ProductDetailDto productDetailDto) {
        // 우선 코드로 조회, 없으면 이름으로 조회
        return productRepository.findByCode(productDetailDto.getCode())
                .or(() -> productRepository.findByName(productDetailDto.getName()))
                .orElseThrow(() -> new IllegalArgumentException("Product not found with code or name: "
                        + productDetailDto.getCode() + ", " + productDetailDto.getName()));
    }


    private ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertProductToDTO)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 제품을 찾을 수 없습니다."));
    }


    /**
     * @param processRoutingDTO
     * @return
     */
    public ProcessRoutingDetailDTO updateProcessRouting(ProcessRoutingDetailDTO processRoutingDTO) {
//        // 1. 기존 라우팅을 조회
//        ProcessRouting existingRouting = processRoutingRepository.findById(processRoutingDTO.getId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공정경로를 찾을 수 없습니다: " + processRoutingDTO.getId()));
//
//        // 2. 기본 필드 업데이트
//        existingRouting.setCode(processRoutingDTO.getCode());
//        existingRouting.setName(processRoutingDTO.getName());
//        existingRouting.setDescription(processRoutingDTO.getDescription());
//        existingRouting.setStandard(processRoutingDTO.isStandard());
//        existingRouting.setActive(processRoutingDTO.isActive());
//
//        // 3. 연관된 RoutingSteps 업데이트
//        List<RoutingStep> updatedRoutingSteps = createRoutingSteps(processRoutingDTO.getRoutingSteps(), existingRouting);
//
//        // 기존의 RoutingSteps를 제거하고 새로 추가
//        existingRouting.getRoutingSteps().clear();
//        existingRouting.getRoutingSteps().addAll(updatedRoutingSteps);
//
////        // 4. 연관된 Products 업데이트
////        List<Product> updatedProducts = processRoutingDTO.getProducts().stream()
////                .map(this::convertProductDtoToEntity)
////                .collect(Collectors.toList());
//
//        // 기존의 Products를 제거하고 새로 추가
//        existingRouting.getProducts().clear();
////        existingRouting.getProducts().addAll(updatedProducts);
//
//        // 5. 변경된 엔티티 저장 및 반환
//        ProcessRouting savedRouting = processRoutingRepository.save(existingRouting);
//        return convertToDTO(savedRouting);

        return null;

    }


    // 메모리 내에서 삭제된 이력을 저장할 리스트
    private final List<ProcessRoutingDTO> deletedRoutingsHistory = new ArrayList<>();

    public ProcessRoutingDTO deleteProcessRouting(Long id) {
        // 1. 기존 라우팅을 조회
        ProcessRouting existingRouting = processRoutingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공정경로를 찾을 수 없습니다: " + id));

        // 2. isActive가 true인 경우 삭제 불가
        if (existingRouting.isActive()) {
            throw new IllegalStateException("사용 중인 공정경로는 삭제가 불가합니다.");
        }

        // 3. 백업 데이터를 이력 리스트에 저장
        ProcessRoutingDTO historyDTO = convertToDTO(existingRouting);
        historyDTO.setDeletedAt(LocalDateTime.now());

        // 이력 리스트에 추가
        deletedRoutingsHistory.add(historyDTO);

        // 4. 라우팅 삭제
        processRoutingRepository.delete(existingRouting);

        // 5. 이력 반환
        return historyDTO;
    }

    // 삭제된 이력들을 반환하는 메서드
    public List<ProcessRoutingDTO> getDeletedRoutingsHistory() {
        return new ArrayList<>(deletedRoutingsHistory);
    }

// ========================== Entity -> DTO ============================
    // ProcessRouting -> DTO 메서드
    public ProcessRoutingDTO convertToDTO(ProcessRouting processRouting) {

        return ProcessRoutingDTO.builder()
                .id(processRouting.getId())
                .code(processRouting.getCode())
                .name(processRouting.getName())
                .description(processRouting.getDescription())
                .isStandard(processRouting.isStandard())
                .isActive(processRouting.isActive())
                .routingSteps(
                        processRouting.getRoutingSteps().stream()
                                .map(this::convertToRoutingStepDTO)
                                .collect(Collectors.toList())
                )
                .products(
                        processRouting.getProducts().stream()
                                .map(this::convertProductToDTO)
                                .collect(Collectors.toList())
                ) // 변환된 Product 목록 추가
                .build();
    }

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
//                .workcenterDTOList(processDetails.getWorkcenters().stream()
//                        .map(this::convertWorkcenterToDTO).toList())
                .build();
    }

    // RoutingStep Entity -> DTO
    private RoutingStepDTO convertToRoutingStepDTO(RoutingStep routingStep) {
        RoutingStepId id = routingStep.getId();
        return RoutingStepDTO.builder()
                .id(id)
                .stepOrder(routingStep.getStepOrder())
                .build();
    }


    // Workcenter Entity -> DTO
    private WorkcenterDTO convertWorkcenterToDTO(Workcenter workcenter) {
        return WorkcenterDTO.builder()
                .id(workcenter.getId())
                .code(workcenter.getCode())
                .name(workcenter.getName())
                .isActive(workcenter.getIsActive())
                .build();
    }

    // Product Entity -> DTO
    private ProductDto convertProductToDTO(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
//                .productType(product.getProductType())
//                .unit(product.getUnit())
//                .standard(product.getStandard())
                .build();
    }


// ========================== DTO -> Entity ============================

    // 1. ProcessRoutingDTO
    public ProcessRouting convertToEntity(ProcessRoutingDTO dto) {
        ProcessRouting processRouting = ProcessRouting.builder()
                .id(dto.getId())
                .code(dto.getCode()) // Routing 지정코드
                .name(dto.getName()) // Routing 이름
                .description(dto.getDescription()) // Routing 설명
                .isStandard(dto.isStandard()) // 표준 여부
                .isActive(dto.isActive()) // 사용 여부
                .build();

//        // 라우팅 먼저 저장해서 id 생성한 후 step 복합 키 생성해야 하므로 별도 처리
//        // DTO의 RoutingStepDTO 리스트를 RoutingStep 엔티티 리스트로 변환
//        List<RoutingStep> routingSteps = dto.getRoutingStepDTOList().stream()
//                .map(stepDTO -> convertDTOToRoutingStep(stepDTO, processRouting)) // 각 RoutingStepDTO를 RoutingStep으로 변환
//                .collect(Collectors.toList());

//        // DTO의 ProductDto 리스트를 Product 엔티티 리스트로 변환
//        List<Product> products = dto.getProducts().stream()
//                .map(this::convertProductDtoToEntity)
//                .collect(Collectors.toList());

        // 변환된 엔티티 리스트들을 ProcessRouting 엔티티에 설정
//        processRouting.setRoutingSteps(routingSteps); // 변환된 RoutingStep 목록 설정
//        processRouting.setProducts(products); // 변환된 Product 목록 설정 // TODO emp 순환참조로 인해 주석처리(임시)

        return processRouting;
    }

    private RoutingStep convertDTOToRoutingStep(RoutingStepDTO dto, ProcessRouting processRouting) {
        Long processId = dto.getId().getProcessId(); // id에서 processId 추출

        ProcessDetails processDetails = processDetailsRepository.findById(processId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 공정 ID: " + processId));

        RoutingStepId routingStepId = new RoutingStepId(processRouting.getId(), processId);

        return RoutingStep.builder()
                .id(routingStepId) // 복합 키 설정
                .processRouting(processRouting)
                .processDetails(processDetails)
                .stepOrder(dto.getStepOrder())
                .build();
    }

}
