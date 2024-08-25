package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// TODO
@Service
@RequiredArgsConstructor
@Transactional
public class ProcessDetailsServiceImpl implements ProcessDetailsService {
    private final ProcessDetailsRepository processDetailsRepository;

    @Override
    public List<ProcessDetailsDTO> getAllProcessDetails() {
        List<ProcessDetails> processDetailsList = processDetailsRepository.findAll();

        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsList.stream()
                .map(this::convertToDTO)
                .toList();

        return processDetailsDTOs;
    }

    @Override
    public ProcessDetailsDTO getProcessDetailsById(Long id) {
        ProcessDetails processDetails = processDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Process not found"));

        // 품질 검사와 작업 실적 기록에 따라 표준 소요 시간, 공정 수행 비용, 평균 불량률 계산
        double calculatedDuration = calculateDuration(processDetails);
        BigDecimal calculatedCost = calculateCost(processDetails);
        double calculatedDefectRate = calculateDefectRate(processDetails);

        // 계산된 값을 엔티티에 설정
        processDetails.setDuration(calculatedDuration);
        processDetails.setCost(calculatedCost);
        processDetails.setDefectRate(calculatedDefectRate);

        return convertToDTO(processDetails);
    }

    private double calculateDuration(ProcessDetails processDetails) {
        // 작업 실적 기록 목록을 가져와서 평균 시간을 계산
//        List<Double> workTimes = getWorkTimesForProcess(processDetails); // 실제 기록을 가져오는 메서드

        // if (workTimes.isEmpty())
        return 0.0; // 기록이 없는 경우 기본값 반환

        // 평균 시간 계산
//        double totalTime = workTimes.stream().mapToDouble(Double::doubleValue).sum();
//        return totalTime / workTimes.size();
    }

    private BigDecimal calculateCost(ProcessDetails processDetails) {
        // 각 작업 단계에서 소모된 비용 목록을 가져와서 합산

        // if (costs.isEmpty())
        return BigDecimal.ZERO; // 예시 값

        // 비용 합산 계산
    }

    private double calculateDefectRate(ProcessDetails processDetails) {
        // 생산 실적 기록에서 총 생산량을 가져와 불량률 계산
        // 1. 총 생산량 메서드 -> totalCounts
        // 2. 불량품 수 메서드 -> defectCounts
//        int totalDefects = defectCounts.stream().mapToInt(Integer::intValue).sum();
//        int totalProduced = totalCounts.stream().mapToInt(Integer::intValue).sum();
        return 0.0;
        // if (defectCounts.isEmpty() || totalCounts.isEmpty())
        // if (totalProduced == 0)

        // 불량률 계산 (불량품 수 / 총 생산량)
        // return (double) totalDefects / totalProduced;
    }


    @Override
    public ProcessDetailsDTO createProcessDetails(ProcessDetailsDTO processDetailsDTO) {
        ProcessDetails processDetails = convertToEntity(processDetailsDTO);
        ProcessDetails savedProcessDetails = processDetailsRepository.save(processDetails);
        return convertToDTO(savedProcessDetails);
    }

    @Override
    public ProcessDetailsDTO updateProcessDetails(Long id, ProcessDetailsDTO processDetailsDTO) {
        return null; // TODO
    }

    @Override
    public void deleteProcessDetails(Long id) {
        // TODO
    }

    // DTO 변환 메서드
    private ProcessDetailsDTO convertToDTO(ProcessDetails processDetails) {
        return ProcessDetailsDTO.builder()
                .id(processDetails.getId())  // 엔티티의 ID를 DTO에 설정
                .code(processDetails.getCode())  // 공정 코드
                .name(processDetails.getName())  // 공정 이름
                .isOutsourced(processDetails.getIsOutsourced())  // 외주 여부
                .duration(processDetails.getDuration())  // 소요 시간
                .cost(processDetails.getCost())  // 공정 비용
                .defectRate(processDetails.getDefectRate())  // 불량률
                .description(processDetails.getDescription())  // 설명
                .isUsed(processDetails.getIsUsed())  // 사용 여부
                .workcenterDTOList(
                        processDetails.getWorkcenters().stream()
                                .map(this::convertToWorkcenterDTO)  // Workcenter를 WorkcenterDTO로 변환
                                .collect(Collectors.toList())
                )
                .routingStepDTOList(
                        processDetails.getRoutingSteps().stream().map(this::convertToRoutingStepDTO).toList()
                )
                .build();
    }

    private RoutingStepDTO convertToRoutingStepDTO(RoutingStep routingStep) {
        RoutingStepId id = routingStep.getId();
        return RoutingStepDTO.builder()
                .routingId(id.getProductionRoutingId())
                .processId(id.getProcessId())
                .stepOrder(routingStep.getStepOrder())
                .productionRoutingDTO(convertToProductionRoutingDTO(routingStep.getProductionRouting()))  // ProductionRouting을 ProductionRoutingDTO로 변환하여 설정
                .build();
    }

    private ProductionRoutingDTO convertToProductionRoutingDTO(ProductionRouting productionRouting) {
        return ProductionRoutingDTO.builder()
                .id(productionRouting.getId())
                .code(productionRouting.getCode())
                .name(productionRouting.getName())
                .isStandard(productionRouting.isStandard())
                .isActive(productionRouting.isActive())
                .routingStepDTOList(
                        productionRouting.getRoutingSteps() != null ?
                                productionRouting.getRoutingSteps().stream()
                                        .map(this::convertToRoutingStepDTO)
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .products(
                        productionRouting.getProducts() != null ?
                                productionRouting.getProducts().stream()
                                        .map(this::convertToProductDetailDto)
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

    private ProductDetailDto convertToProductDetailDto(Product product) {
        return ProductDetailDto.createProductDetailDto(product);
    }

    private WorkcenterDTO convertToWorkcenterDTO(Workcenter workcenter) {
        return WorkcenterDTO.builder()
                .id(workcenter.getId())  // Workcenter의 ID
                .name(workcenter.getName())  // Workcenter 이름
                .code(workcenter.getCode())
                .build();
    }

    private ProcessDetails convertToEntity(ProcessDetailsDTO processDetailsDTO) {
        return ProcessDetails.builder()
                .id(processDetailsDTO.getId())  // DTO의 ID를 엔티티에 설정
                .code(processDetailsDTO.getCode())  // 공정 코드
                .name(processDetailsDTO.getName())  // 공정 이름
                .isOutsourced(processDetailsDTO.getIsOutsourced())  // 외주 여부
                .duration(processDetailsDTO.getDuration())  // 소요 시간
                .cost(processDetailsDTO.getCost())  // 공정 비용
                .defectRate(processDetailsDTO.getDefectRate())  // 불량률
                .description(processDetailsDTO.getDescription())  // 설명
                .isUsed(processDetailsDTO.getIsUsed())  // 사용 여부
                .workcenters(
                        processDetailsDTO.getWorkcenterDTOList() != null ?
                                processDetailsDTO.getWorkcenterDTOList().stream()
                                        .map(this::convertToWorkcenter)
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

    private Workcenter convertToWorkcenter(WorkcenterDTO workcenterDTO) {
        return Workcenter.builder()
                .id(workcenterDTO.getId())
                .code(workcenterDTO.getCode())
                .name(workcenterDTO.getName())
                .workcenterType(workcenterDTO.getWorkcenterType())
                .build();
    }
}
