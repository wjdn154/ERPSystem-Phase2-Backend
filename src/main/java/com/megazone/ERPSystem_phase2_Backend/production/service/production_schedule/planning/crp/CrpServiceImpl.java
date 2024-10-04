package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.crp;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Crp;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.CrpDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.crp.CrpRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CrpServiceImpl implements CrpService {

    private final CrpRepository crpRepository;
    private final MpsRepository mpsRepository;
    private final WorkcenterRepository workcenterRepository;

    @Override
    public CrpDTO createCrp(CrpDTO crpDto) {
        if (crpDto == null) {
            throw new IllegalArgumentException("CRP 정보가 입력되지 않았습니다.");
        }
        if (crpDto.getMpsId() == null) {
            throw new IllegalArgumentException("MPS ID가 필요합니다.");
        }
        if (crpDto.getWorkcenterId() == null) {
            throw new IllegalArgumentException("작업장 ID가 필요합니다.");
        }

        // MPS 조회
        Mps mps = mpsRepository.findById(crpDto.getMpsId())
                .orElseThrow(() -> new EntityNotFoundException("해당 MPS를 찾을 수 없습니다."));

        // 작업장 조회
        Workcenter workCenter = workcenterRepository.findById(crpDto.getWorkcenterId())
                .orElseThrow(() -> new EntityNotFoundException("해당 작업장을 찾을 수 없습니다."));

        // CRP 생성
        Crp crp = Crp.builder()
                .mps(mps)
                .workcenter(workCenter)
                .requiredCapacity(crpDto.getRequiredCapacity())
                .availableCapacity(crpDto.getAvailableCapacity())
                .capacityLoadPercentage(crpDto.getCapacityLoadPercentage())
                .capacityStatus(crpDto.getCapacityStatus())
                .remarks(crpDto.getRemarks())
                .build();

        Crp savedCrp = crpRepository.save(crp);

        return convertToDto(savedCrp);
    }

    @Override
    public CrpDTO getCrpById(Long id) {
        Crp crp = crpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 CRP를 찾을 수 없습니다."));
        return convertToDto(crp);
    }

    @Override
    public List<CrpDTO> getAllCrps() {
        List<Crp> crpList = crpRepository.findAll();
        return crpList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CrpDTO updateCrp(Long id, CrpDTO crpDto) {
        // 검증 로직
        if (crpDto == null) {
            throw new IllegalArgumentException("CRP 정보가 입력되지 않았습니다.");
        }

        Crp existingCrp = crpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 CRP를 찾을 수 없습니다."));

        // 업데이트할 필드만 업데이트
        if (crpDto.getRequiredCapacity() != null) {
            existingCrp.setRequiredCapacity(crpDto.getRequiredCapacity());
        }
        if (crpDto.getAvailableCapacity() != null) {
            existingCrp.setAvailableCapacity(crpDto.getAvailableCapacity());
        }
        if (crpDto.getCapacityLoadPercentage() != null) {
            existingCrp.setCapacityLoadPercentage(crpDto.getCapacityLoadPercentage());
        }
        if (crpDto.getCapacityStatus() != null) {
            existingCrp.setCapacityStatus(crpDto.getCapacityStatus());
        }
        if (crpDto.getRemarks() != null) {
            existingCrp.setRemarks(crpDto.getRemarks());
        }

        Crp updatedCrp = crpRepository.save(existingCrp);

        return convertToDto(updatedCrp);
    }

    @Override
    @Transactional
    public void deleteCrp(Long id) {
        Crp existingCrp = crpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 CRP를 찾을 수 없습니다."));

        crpRepository.delete(existingCrp);
    }

    // DTO로 변환 메서드
    private CrpDTO convertToDto(Crp crp) {
        return CrpDTO.builder()
                .id(crp.getId())
                .mpsId(crp.getMps().getId())
                .workcenterId(crp.getWorkcenter().getId())
                .requiredCapacity(crp.getRequiredCapacity())
                .availableCapacity(crp.getAvailableCapacity())
                .capacityLoadPercentage(crp.getCapacityLoadPercentage())
                .capacityStatus(crp.getCapacityStatus())
                .remarks(crp.getRemarks())
                .build();
    }
}
