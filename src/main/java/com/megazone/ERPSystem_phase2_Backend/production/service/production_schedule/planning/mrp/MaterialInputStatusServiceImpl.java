package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MaterialInputStatusDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.MaterialInputStatus;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mrp.MaterialInputStatusRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialInputStatusServiceImpl implements MaterialInputStatusService {

    private final MaterialInputStatusRepository materialInputStatusRepository;
    private final MaterialDataRepository materialDataRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final ProcessDetailsRepository processDetailsRepository;
    private final WorkcenterRepository workcenterRepository;
    private final EquipmentDataRepository equipmentDataRepository;

    @Override
    @Transactional
    public MaterialInputStatusDto createMaterialInputStatus(MaterialInputStatusDto dto) {
        // 검증 로직
        if (dto == null) {
            throw new IllegalArgumentException("자재 투입 현황 정보가 입력되지 않았습니다.");
        }
//        if (dto.getMaterialDataId() == null) {
//            throw new IllegalArgumentException("자재 ID가 필요합니다.");
//        }
        if (dto.getProductionOrderId() == null) {
            throw new IllegalArgumentException("작업지시 ID가 필요합니다.");
        }
        if (dto.getProcessDetailsId() == null) {
            throw new IllegalArgumentException("공정 상세 ID가 필요합니다.");
        }
        if (dto.getWorkcenterId() == null) {
            throw new IllegalArgumentException("작업장 ID가 필요합니다.");
        }
        if (dto.getQuantityConsumed() == null || dto.getQuantityConsumed().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("소비된 자재 수량이 올바르지 않습니다.");
        }

//        // 연관 엔티티 조회
//        MaterialData materialData = materialDataRepository.findById(dto.getMaterialDataId())
//                .orElseThrow(() -> new EntityNotFoundException("해당 자재를 찾을 수 없습니다."));
        ProductionOrder productionOrder = productionOrderRepository.findById(dto.getProductionOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당 작업지시를 찾을 수 없습니다."));
        ProcessDetails processDetails = processDetailsRepository.findById(dto.getProcessDetailsId())
                .orElseThrow(() -> new EntityNotFoundException("해당 공정 상세를 찾을 수 없습니다."));
        Workcenter workCenter = workcenterRepository.findById(dto.getWorkcenterId())
                .orElseThrow(() -> new EntityNotFoundException("해당 작업장을 찾을 수 없습니다."));
        EquipmentData equipmentData = null;
        if (dto.getEquipmentDataId() != null) {
            equipmentData = equipmentDataRepository.findById(dto.getEquipmentDataId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 설비를 찾을 수 없습니다."));
        }

        // MaterialInputStatus 생성
        MaterialInputStatus inputStatus = MaterialInputStatus.builder()
                .name(dto.getName())
                .dateTime(dto.getDateTime())
//                .materialData(materialData)
                .productionOrder(productionOrder)
                .processDetails(processDetails)
                .workcenter(workCenter)
//                .equipmentData(equipmentData)
                .quantityConsumed(dto.getQuantityConsumed())
                .unitOfMeasure(dto.getUnitOfMeasure())
                .remarks(dto.getRemarks())
                .build();

        MaterialInputStatus savedInputStatus = materialInputStatusRepository.save(inputStatus);

//        // 재고 수준 업데이트
//        BigDecimal newOnHandQuantity = materialData.getOnHandQuantity().subtract(dto.getQuantityConsumed());
//        if (newOnHandQuantity.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("재고 수량이 부족합니다.");
//        }
//        materialData.setOnHandQuantity(newOnHandQuantity);
//        materialDataRepository.save(materialData);
//
//        // 재고 부족 시 구매 요청 생성
//        if (newOnHandQuantity.compareTo(materialData.getReorderPoint()) <= 0) {
//            purchaseRequestService.createPurchaseRequest(materialData.getId(), materialData.getReorderQuantity());
//        }

        return convertToDto(savedInputStatus);
    }

    @Override
    public MaterialInputStatusDto getMaterialInputStatusById(Long id) {
        MaterialInputStatus inputStatus = materialInputStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 자재 투입 현황을 찾을 수 없습니다."));
        return convertToDto(inputStatus);
    }

    @Override
    public List<MaterialInputStatusDto> getAllMaterialInputStatuses() {
        List<MaterialInputStatus> inputStatusList = materialInputStatusRepository.findAll();
        return inputStatusList.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public MaterialInputStatusDto updateMaterialInputStatus(Long id, MaterialInputStatusDto dto) {
        // 검증 로직
        if (dto == null) {
            throw new IllegalArgumentException("자재 투입 현황 정보가 입력되지 않았습니다.");
        }

        MaterialInputStatus existingInputStatus = materialInputStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 자재 투입 현황을 찾을 수 없습니다."));

        // 업데이트할 필드만 업데이트
        if (dto.getName() != null) {
            existingInputStatus.setName(dto.getName());
        }
        if (dto.getDateTime() != null) {
            existingInputStatus.setDateTime(dto.getDateTime());
        }
        if (dto.getQuantityConsumed() != null) {
            existingInputStatus.setQuantityConsumed(dto.getQuantityConsumed());
            // 재고 수준 재계산 필요
            // 복잡한 로직이므로 여기서는 생략하거나 별도의 메서드로 처리
        }
        if (dto.getUnitOfMeasure() != null) {
            existingInputStatus.setUnitOfMeasure(dto.getUnitOfMeasure());
        }
        if (dto.getRemarks() != null) {
            existingInputStatus.setRemarks(dto.getRemarks());
        }
        // 연관 엔티티 변경은 복잡한 로직이 필요하므로 여기서는 생략하거나 추가 검증이 필요함

        MaterialInputStatus updatedInputStatus = materialInputStatusRepository.save(existingInputStatus);

        return convertToDto(updatedInputStatus);
    }

    @Override
    @Transactional
    public void deleteMaterialInputStatus(Long id) {
        MaterialInputStatus existingInputStatus = materialInputStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 자재 투입 현황을 찾을 수 없습니다."));

        // 삭제 시 재고 수량 복원 등의 로직이 필요할 수 있음
        materialInputStatusRepository.delete(existingInputStatus);
    }

    // DTO로 변환 메서드
    private MaterialInputStatusDto convertToDto(MaterialInputStatus inputStatus) {
        return MaterialInputStatusDto.builder()
                .id(inputStatus.getId())
                .name(inputStatus.getName())
                .dateTime(inputStatus.getDateTime())
//                .materialDataId(inputStatus.getMaterialData().getId())
                .productionOrderId(inputStatus.getProductionOrder().getId())
                .processDetailsId(inputStatus.getProcessDetails().getId())
                .workcenterId(inputStatus.getWorkcenter().getId())
                .equipmentDataId(inputStatus.getEquipmentData() != null ? inputStatus.getEquipmentData().getId() : null)
                .quantityConsumed(inputStatus.getQuantityConsumed())
                .unitOfMeasure(inputStatus.getUnitOfMeasure())
                .remarks(inputStatus.getRemarks())
                .build();
    }
}
