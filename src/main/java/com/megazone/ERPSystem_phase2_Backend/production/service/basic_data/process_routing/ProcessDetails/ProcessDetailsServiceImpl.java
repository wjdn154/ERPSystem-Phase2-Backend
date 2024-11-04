package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingBasicDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO
@Service
@RequiredArgsConstructor
@Transactional
public class ProcessDetailsServiceImpl implements ProcessDetailsService {
    private final ProcessDetailsRepository processDetailsRepository;
    private final WorkcenterRepository workcenterRepository;
    private final ProductRepository productRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

    @Override
    public List<ProcessDetailsDTO> getAllProcessDetails() {
        List<ProcessDetails> processDetailsList = processDetailsRepository.findAll();

        List<ProcessDetailsDTO> processDetailsDTOs = processDetailsList.stream()
                .map(this::convertToDTO)
                .toList();

        return processDetailsDTOs;
    }

    @Override
    public List<ProcessDetailsDTO> findByNameContaining(String name) {
        List<ProcessDetails> processes = processDetailsRepository.findByNameContaining(name);
        return processes.stream()
                .map(this::convertToDTO).toList();
    }

    @Override
    public Optional<ProcessDetailsDTO> getProcessDetailsByCode(String code) {
        ProcessDetails processDetails = processDetailsRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("해당 생산공정 " + code + "을 찾을 수 없습니다."));

//        // 품질 검사와 작업 실적 기록에 따라 표준 소요 시간, 공정 수행 비용, 평균 불량률 계산
//        double calculatedDuration = calculateDuration(processDetails);
//        BigDecimal calculatedCost = calculateCost(processDetails);
//        double calculatedDefectRate = calculateDefectRate(processDetails);
//
//        // 계산된 값을 엔티티에 설정
//        processDetails.setDuration(calculatedDuration);
//        processDetails.setCost(calculatedCost);
//        processDetails.setDefectRate(calculatedDefectRate);
//
//        // 변경 사항을 데이터베이스에 저장
//        ProcessDetails savedProcessDetails = processDetailsRepository.save(processDetails);

        return Optional.ofNullable(convertToDTO(processDetails));
    }

    private double calculateDuration(ProcessDetailsDTO dto) {
        // 작업 실적 기록 목록을 가져와서 평균 시간을 계산
//        List<Double> workTimes = getWorkTimesForProcess(processDetails); // 실제 기록을 가져오는 메서드

        // if (workTimes.isEmpty())
        return 0.0; // 기록이 없는 경우 기본값 반환

        // 평균 시간 계산
//        double totalTime = workTimes.stream().mapToDouble(Double::doubleValue).sum();
//        return totalTime / workTimes.size();
    }

    private BigDecimal calculateCost(ProcessDetailsDTO dto) {
        // 각 작업 단계에서 소모된 비용 목록을 가져와서 합산

        // if (costs.isEmpty())
        return BigDecimal.ZERO; // 예시 값

        // 비용 합산 계산
    }

    private double calculateDefectRate(ProcessDetailsDTO dto) {
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
        if (processDetailsRepository.existsByCode(processDetailsDTO.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다: " + processDetailsDTO.getCode());
        }

        // 사용자가 입력하지 않은 경우에만 자동 계산 로직 적용
        double duration = (processDetailsDTO.getDuration() != null) ?
                processDetailsDTO.getDuration() : calculateDuration(processDetailsDTO);

        BigDecimal cost = (processDetailsDTO.getCost() != null) ?
                processDetailsDTO.getCost() : calculateCost(processDetailsDTO);

        double defectRate = (processDetailsDTO.getDefectRate() != null) ?
                processDetailsDTO.getDefectRate() : calculateDefectRate(processDetailsDTO);

        // 엔티티로 변환 후 저장
        ProcessDetails processDetails = convertToEntity(processDetailsDTO);
        processDetails.setDuration(duration);
        processDetails.setCost(cost);
        processDetails.setDefectRate(defectRate);

        ProcessDetails savedProcessDetails = processDetailsRepository.save(processDetails);

        return convertToDTO(savedProcessDetails);
    }

    @Override
    public ProcessDetailsDTO updateByCode(String code, ProcessDetailsDTO processDetailsDTO) {
        // 1. CODE를 사용해 기존 ProcessDetails 엔티티를 조회
        ProcessDetails existingProcessDetails = processDetailsRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("해당 코드 " + code + "의 공정을 찾을 수 없습니다."));

        // 2. ProcessDetailsDTO에서 받은 데이터를 사용해 필드를 업데이트
        existingProcessDetails.setCode(processDetailsDTO.getCode());
        existingProcessDetails.setName(processDetailsDTO.getName());
        existingProcessDetails.setDuration(processDetailsDTO.getDuration());
        existingProcessDetails.setCost(processDetailsDTO.getCost());
        existingProcessDetails.setDefectRate(processDetailsDTO.getDefectRate());
        existingProcessDetails.setIsUsed(processDetailsDTO.getIsUsed());
        existingProcessDetails.setDescription(processDetailsDTO.getDescription());  // 설명 반영
        existingProcessDetails.setIsOutsourced(processDetailsDTO.getIsOutsourced());  // 외주 여부 반영

        try {
            // 3. 변경된 엔티티를 데이터베이스에 저장
            ProcessDetails updatedProcessDetails = processDetailsRepository.save(existingProcessDetails);

            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription(updatedProcessDetails.getName() + "생산공정 정보가 변경")
                    .activityType(ActivityType.PRODUCTION)
                    .activityTime(LocalDateTime.now())
                    .build());

            notificationService.createAndSendNotification(
                    ModuleType.PRODUCTION,
                    PermissionType.ALL,
                    updatedProcessDetails.getName() + "생산공정 정보가 변경되었습니다.",
                    NotificationType.UPDATE_ROUTING_DETAIL);

            // 4. 수정된 엔티티를 DTO로 변환하여 반환
            return convertToDTO(updatedProcessDetails);
        } catch (DataIntegrityViolationException e) {
            // 데이터베이스에서 고유성 위반이 발생할 경우 예외 처리
            throw new IllegalArgumentException("이미 존재하는 생산공정입니다.");
        }
    }

    @Override
    @Transactional
    public ProcessDetailsDTO deleteByCode(String code) {
        // 1. Code를 사용해 기존 ProcessDetails 엔티티를 조회
        ProcessDetails processDetails = processDetailsRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Process with code " + code + " not found"));

        if (processDetails.getIsUsed()) {
            throw new IllegalArgumentException("해당 공정은 사용 중이므로 삭제할 수 없습니다: " + code);
        }

        // 2. 삭제하기 전에 엔티티를 DTO로 변환
        ProcessDetailsDTO deletedProcessDetailsDTO = convertToDTO(processDetails);


        // 3. 엔티티 삭제
        processDetailsRepository.delete(processDetails);

        // 4. 삭제된 엔티티의 정보를 반환
        return deletedProcessDetailsDTO;
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
//                .workcenterDTOList(
//                        processDetails.getWorkcenters().stream()
//                                .map(this::convertToWorkcenterDTO)  // Workcenter를 WorkcenterDTO로 변환
//                                .collect(Collectors.toList())
//                )
//                .routingStepIdList(
//                        processDetails.getRoutingSteps().stream()
//                                .map(RoutingStep::getId)
//                                .collect(Collectors.toList())
//                )
                .build();
    }

    private RoutingStepDTO convertToRoutingStepDTO(RoutingStep routingStep) {
        RoutingStepId id = routingStep.getId();
        return RoutingStepDTO.builder()
                .id(routingStep.getId())
//                .routingId(id.getProcessRoutingId())
//                .processId(id.getProcessId())
                .stepOrder(routingStep.getStepOrder())
//                .processRoutingBasicDTO(convertToProcessRoutingBasicDTO(routingStep.getProcessRouting()))  // ProcessRouting의 기본 정보만 변환
                .build();
    }

    private WorkcenterDTO convertToWorkcenterDTO(Workcenter workcenter) {
        return WorkcenterDTO.builder()
                .id(workcenter.getId())  // Workcenter의 ID
                .name(workcenter.getName())  // Workcenter 이름
                .code(workcenter.getCode())
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
//                .workcenters(
//                        processDetailsDTO.getWorkcenterDTOList() != null ?
//                                processDetailsDTO.getWorkcenterDTOList().stream()
//                                        .map(this::convertToWorkcenter)
//                                        .collect(Collectors.toList())
//                                : Collections.emptyList()
//                )
                .build();
    }
}
