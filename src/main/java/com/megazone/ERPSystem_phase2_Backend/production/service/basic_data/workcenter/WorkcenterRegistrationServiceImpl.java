package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkcenterRegistrationServiceImpl implements WorkcenterRegistrationService {

    private final WorkcenterRepository workcenterRepository;

    /**
     * 저장 시 중복코드 검증
     * @param workcenter 저장할 작업장 객체
     * @return
     */
    @Override
    public Workcenter save(Workcenter workcenter) {
        try {
            return workcenterRepository.save(workcenter);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("이미 존재하는 코드입니다: " + workcenter.getCode());
        }
    }

    /**
     * 삭제 시 사용 중인 작업장 확인
     * @param id 삭제할 Workcenter ID
     */
    @Override
    public void deleteById(Long id) {
        Workcenter workcenter = workcenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("작업장 ID: " + id + "를 찾을 수 없습니다."));
        if (workcenter.getIsActive()) {
            throw new RuntimeException("사용 중인 작업장은 삭제할 수 없습니다. ID: " + id);
        }
        workcenterRepository.deleteById(id);
    }

    @Override
    public Workcenter updateWorkcenter(String code, WorkcenterDTO workcenterDTO) {
        // 1. 작업장 코드를 사용하여 기존 작업장을 조회
        Workcenter existingWorkcenter = workcenterRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("작업장 코드: " + code + "를 찾을 수 없습니다."));

        // 2. 작업장 코드가 변경되었을 경우 중복 검사를 수행
        if (!existingWorkcenter.getCode().equals(workcenterDTO.getCode())) {
            if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
                throw new RuntimeException("코드 " + workcenterDTO.getCode() + "는 이미 존재합니다.");
            }
            existingWorkcenter.setCode(workcenterDTO.getCode());
        }

        // 3. 나머지 필드 업데이트
        existingWorkcenter.setName(workcenterDTO.getName());
        existingWorkcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
        existingWorkcenter.setDescription(workcenterDTO.getDescription());
        existingWorkcenter.setIsActive(workcenterDTO.getIsActive());

        // 4. 공장과 공정 코드가 변경되었을 경우 처리
        if (workcenterDTO.getFactoryCode() != null) {
            Warehouse factory = new Warehouse();
            factory.setCode(workcenterDTO.getFactoryCode());
            existingWorkcenter.setFactory(factory);
        }

        if (workcenterDTO.getProcessCode() != null) {
            ProcessDetails processDetails = new ProcessDetails();
            processDetails.setCode(workcenterDTO.getProcessCode());
            existingWorkcenter.setProcessDetails(processDetails);
        }

//        // 4. 공장과 공정 코드가 변경되었을 경우 처리
//        if (workcenterDTO.getFactoryCode() != null) {
//            Warehouse factory = workcenterRepository.findFactoryByCode(workcenterDTO.getFactoryCode())
//                    .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode() + "를 찾을 수 없습니다."));
//            existingWorkcenter.setFactory(factory);
//        }
//
//        if (workcenterDTO.getProcessCode() != null) {
//            ProcessDetails processDetails = workcenterRepository.findProcessByCode(workcenterDTO.getProcessCode())
//                    .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode() + "를 찾을 수 없습니다."));
//            existingWorkcenter.setProcessDetails(processDetails);
//        }

        // 5. 작업장 업데이트 후 저장
        return workcenterRepository.save(existingWorkcenter);
    }

}
