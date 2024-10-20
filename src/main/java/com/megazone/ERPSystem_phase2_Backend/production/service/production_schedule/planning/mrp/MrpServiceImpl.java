package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MrpDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.Mrp;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mrp.MrpRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MrpServiceImpl implements MrpService {

    MrpRepository mrpRepository;
    MpsRepository mpsRepository;
    MaterialDataRepository materialDataRepository;

    @Override
    public MrpDTO createMrp(MrpDTO mrpDto) {
        // 검증 로직
        if (mrpDto == null) {
            throw new IllegalArgumentException("MRP 정보가 입력되지 않았습니다.");
        }
        if (mrpDto.getMpsId() == null) {
            throw new IllegalArgumentException("MPS ID가 필요합니다.");
        }
        if (mrpDto.getMaterialDataId() == null) {
            throw new IllegalArgumentException("자재 ID가 필요합니다.");
        }

        // MPS 조회
        Mps mps = mpsRepository.findById(mrpDto.getMpsId())
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다."));

        // 자재 조회
        MaterialData materialData = materialDataRepository.findById(mrpDto.getMaterialDataId())
                .orElseThrow(() -> new EntityNotFoundException("해당 자재를 찾을 수 없습니다."));

        // MRP 생성
        Mrp mrp = Mrp.builder()
//                .mps(mps)
                .materialData(materialData)
                .requiredQuantity(mrpDto.getRequiredQuantity())
                .onHandQuantity(mrpDto.getOnHandQuantity())
                .plannedOrderQuantity(mrpDto.getPlannedOrderQuantity())
                .plannedOrderReleaseDate(mrpDto.getPlannedOrderReleaseDate())
                .plannedOrderReceiptDate(mrpDto.getPlannedOrderReceiptDate())
                .remarks(mrpDto.getRemarks())
                .build();

        Mrp savedMrp = mrpRepository.save(mrp);

        return convertToDto(savedMrp);
    }

    @Override
    public MrpDTO getMrpById(Long id) {
        Mrp mrp = mrpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 MRP를 찾을 수 없습니다."));
        return convertToDto(mrp);
    }

    @Override
    public List<MrpDTO> getAllMrps() {
        List<Mrp> mrpList = mrpRepository.findAll();
        return mrpList.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public MrpDTO updateMrp(Long id, MrpDTO mrpDto) {
        if (mrpDto == null) {
            throw new IllegalArgumentException("MRP 정보가 입력되지 않았습니다.");
        }

        Mrp existingMrp = mrpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 MRP를 찾을 수 없습니다."));

        // 업데이트할 필드만 업데이트
        if (mrpDto.getRequiredQuantity() != null) {
            existingMrp.setRequiredQuantity(mrpDto.getRequiredQuantity());
        }
        if (mrpDto.getOnHandQuantity() != null) {
            existingMrp.setOnHandQuantity(mrpDto.getOnHandQuantity());
        }
        if (mrpDto.getPlannedOrderQuantity() != null) {
            existingMrp.setPlannedOrderQuantity(mrpDto.getPlannedOrderQuantity());
        }
        if (mrpDto.getPlannedOrderReleaseDate() != null) {
            existingMrp.setPlannedOrderReleaseDate(mrpDto.getPlannedOrderReleaseDate());
        }
        if (mrpDto.getPlannedOrderReceiptDate() != null) {
            existingMrp.setPlannedOrderReceiptDate(mrpDto.getPlannedOrderReceiptDate());
        }
        if (mrpDto.getRemarks() != null) {
            existingMrp.setRemarks(mrpDto.getRemarks());
        }

        Mrp updatedMrp = mrpRepository.save(existingMrp);

        return convertToDto(updatedMrp);
    }

    @Override
    @Transactional
    public void deleteMrp(Long id) {
        Mrp existingMrp = mrpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 MRP를 찾을 수 없습니다."));

        mrpRepository.delete(existingMrp);
    }

    private MrpDTO convertToDto(Mrp mrp) {
        return MrpDTO.builder()
                .id(mrp.getId())
//                .mpsId(mrp.getMps().getId())
                .materialDataId(mrp.getMaterialData().getId())
                .requiredQuantity(mrp.getRequiredQuantity())
                .onHandQuantity(mrp.getOnHandQuantity())
                .plannedOrderQuantity(mrp.getPlannedOrderQuantity())
                .plannedOrderReleaseDate(mrp.getPlannedOrderReleaseDate())
                .plannedOrderReceiptDate(mrp.getPlannedOrderReceiptDate())
                .remarks(mrp.getRemarks())
                .build();
    }
}
