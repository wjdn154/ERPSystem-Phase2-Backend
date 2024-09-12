package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request.ProductionRequestsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductionRequestServiceImpl implements ProductionRequestService {

    private final ProductionRequestsRepository productionRequestsRepository;

    @Override
    public ProductionRequestDTO saveManualProductionRequest(ProductionRequestDTO dto) {
        ProductionRequest entity = convertToEntity(dto);
        // 수동 등록의 경우, 추가적인 처리나 검증이 필요할 수 있음
        // 예: 요청자가 지정한 사항을 검토
        ProductionRequest savedEntity = productionRequestsRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public ProductionRequestDTO getProductionRequestById(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        return convertToDTO(entity);
    }

    @Override
    public List<ProductionRequestDTO> getAllProductionRequests() {
        List<ProductionRequest> entities = productionRequestsRepository.findAll();
        return entities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public ProductionRequestDTO updateProductionRequest(Long id, ProductionRequestDTO dto) {
        ProductionRequest existingEntity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        // 엔티티 업데이트
        existingEntity.setName(dto.getName());
        existingEntity.setIsConfirmed(dto.getIsConfirmed());
        existingEntity.setRequestDate(dto.getRequestDate());
        existingEntity.setRequestQuantity(dto.getRequestQuantity());
        existingEntity.setConfirmedQuantity(dto.getConfirmedQuantity());
        existingEntity.setProduct(dto.getProduct());
        existingEntity.setSalesOrder(dto.getSalesOrder());
        existingEntity.setRequester(dto.getRequester());
        existingEntity.setRemarks(dto.getRemarks());

        ProductionRequest updatedEntity = productionRequestsRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteProductionRequest(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        // 확정된 생산 요청이면 삭제할 수 없음
        if (entity.getIsConfirmed()) {
            throw new IllegalStateException("확정된 생산 요청은 삭제할 수 없습니다.");
        }

        productionRequestsRepository.delete(entity);
    }

    /**
     * 자동 생성된 생산 요청 저장
     */
    public ProductionRequestDTO saveAutoProductionRequest(ProductionRequestDTO dto) {
        ProductionRequest entity = convertToEntity(dto);

        // 자동 생성 요청의 경우, 추가적인 처리나 검증이 필요할 수 있음
        // 예: 시스템에 의해 자동으로 생성된 요청임을 표시
        entity.setIsConfirmed(false); // 기본적으로 승인되지 않은 상태로 설정
        ProductionRequest savedEntity = productionRequestsRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    /**
     * 생산 요청 승인
     */
    public ProductionRequestDTO approveProductionRequest(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        entity.setIsConfirmed(true);
        ProductionRequest updatedEntity = productionRequestsRepository.save(entity);
        return convertToDTO(updatedEntity);
    }


    // DTO와 엔티티 변환 메서드
    private ProductionRequest convertToEntity(ProductionRequestDTO dto) {
        return ProductionRequest.builder()
                .id(dto.getId())
                .name(dto.getName())
                .isConfirmed(dto.getIsConfirmed())
                .requestDate(dto.getRequestDate())
                .requestQuantity(dto.getRequestQuantity())
                .confirmedQuantity(dto.getConfirmedQuantity())
                .product(dto.getProduct())
                .salesOrder(dto.getSalesOrder())
                .requester(dto.getRequester())
                .remarks(dto.getRemarks())
                .build();
    }

    private ProductionRequestDTO convertToDTO(ProductionRequest entity) {
        return ProductionRequestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isConfirmed(entity.getIsConfirmed())
                .requestDate(entity.getRequestDate())
                .requestQuantity(entity.getRequestQuantity())
                .confirmedQuantity(entity.getConfirmedQuantity())
                .product(entity.getProduct())
                .salesOrder(entity.getSalesOrder())
                .requester(entity.getRequester())
                .remarks(entity.getRemarks())
                .build();
    }
}
