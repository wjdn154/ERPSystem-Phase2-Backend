package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MpsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.crp.CrpService;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp.MrpService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MpsServiceImpl implements MpsService {

    private final MpsRepository mpsRepository;
    private final ProductRepository productRepository;
    private final MrpService mrpService;
    private final CrpService crpService;
    private final ProductionOrderService productionOrderService;

    @Override
    @Transactional
    public MpsDTO createMps(MpsDTO mpsDto) {
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

        // MPS 생성
        Mps mps = Mps.builder()
                .name(mpsDto.getName())
                .product(product)
                .quantity(mpsDto.getQuantity())
                .startDate(mpsDto.getStartDate())
                .endDate(mpsDto.getEndDate())
                .status(mpsDto.getStatus())
                .remarks(mpsDto.getRemarks())
                .build();

        Mps savedMps = mpsRepository.save(mps);

        return convertToDto(savedMps);
    }

    @Override
    public List<MpsDTO> getAllMps() {
        List<Mps> mpsList = mpsRepository.findAll();
        return mpsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MpsDTO getMpsById(Long id) {
        Mps mps = mpsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다. :" + id));
        return convertToDto(mps);
    }

    @Override
    public MpsDTO updateMps(Long id, MpsDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("MPS 정보가 입력되지 않았습니다.");
        }
        if (dto.getQuantity() != null && dto.getQuantity() <= 0) {
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

            // TODO: MPS 삭제 전 관련된 하위 계획이나 데이터가 있는지 검증하고, 삭제 또는 처리 로직 추가
            // MPS 삭제에 따른 하위 계획 및 지시 삭제 (예시)
            // productionOrderService.deleteProductionOrdersByMps(existingMps);
            // mrpService.deleteMrpByMps(existingMps);
            // crpService.deleteCrpByMps(existingMps);

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
        return MpsDTO.builder()
                .id(mps.getId())
                .name(mps.getName())
                .planDate(mps.getPlanDate())
                .planType(mps.getPlanType())
                .startDate(mps.getStartDate())
                .endDate(mps.getEndDate())
                .status(mps.getStatus())
                .productId(mps.getProduct().getId())
                .quantity(mps.getQuantity())
                .remarks(mps.getRemarks())
                .build();
    }

    private Mps convertToEntity(MpsDTO dto) {

        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수입니다.");
        }

        if (dto.getStatus() == null) {
            throw new IllegalArgumentException("상태는 필수 값입니다.");
        }

        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("품목을 선택해 주세요.");
        }

        return Mps.builder()
                .name(dto.getName())
                .planDate(dto.getPlanDate())
                .planType(dto.getPlanType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .product(productRepository.findById(dto.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("해당 품목을 찾을 수 없습니다.")))
                .quantity(dto.getQuantity())
                .remarks(dto.getRemarks())
                .build();
    }
}
