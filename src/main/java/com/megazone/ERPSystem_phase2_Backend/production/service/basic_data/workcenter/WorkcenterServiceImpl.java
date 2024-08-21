package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkcenterServiceImpl implements WorkcenterService {

    private final WorkcenterRepository workcenterRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProcessDetailsRepository processDetailsRepository;

    /**
     * 저장 시 중복코드 검증
     * @param workcenterDTO 저장할 작업장
     * @return 저장된 Workcenter 객체
     */
    @Override
    public Workcenter save(WorkcenterDTO workcenterDTO) {
        // 1. 작업장 코드 중복 확인
        if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
            throw new RuntimeException("이미 존재하는 작업장 코드입니다: " + workcenterDTO.getCode());
        }

        // 2. WorkcenterDTO를 Workcenter 엔티티로 변환
        Workcenter workcenter = convertToEntity(workcenterDTO);

        // 3. Workcenter 엔티티 저장
        try {
            return workcenterRepository.save(workcenter);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // DTO를 엔티티로 변환하는 메서드
    private Workcenter convertToEntity(WorkcenterDTO workcenterDTO) {
        Workcenter workcenter = new Workcenter();
        workcenter.setCode(workcenterDTO.getCode());
        workcenter.setName(workcenterDTO.getName());
        workcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
        workcenter.setDescription(workcenterDTO.getDescription());
        workcenter.setIsActive(workcenterDTO.getIsActive());

        // processCode를 통해 ProcessDetails를 설정
        if (workcenterDTO.getProcessCode() != null) {
            ProcessDetails processDetails = processDetailsRepository.findByCode(workcenterDTO.getProcessCode().getCode())
                    .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode().getCode() + "에 해당하는 공정을 찾을 수 없습니다."));
            workcenter.setProcessDetails(processDetails);
        }

        // factoryCode를 통해 Warehouse를 설정
        if (workcenterDTO.getFactoryCode() != null) {
            Warehouse factory = warehouseRepository.findByCode(workcenterDTO.getFactoryCode().getCode())
                    .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode().getCode() + "에 해당하는 공장을 찾을 수 없습니다."));
            workcenter.setFactory(factory);
        }

        return workcenter;
    }

    /**
     * 삭제 시 사용 중인 작업장 확인
     * @param ids 삭제할 Workcenter ID
     */
    @Override
    public List<Workcenter> deleteByIds(List<Long> ids) {
        // 삭제 전 작업장들을 미리 조회하여 리스트에 저장
        List<Workcenter> workcentersToDelete = workcenterRepository.findAllById(ids);

        // 사용 중인 작업장이 있는지 확인
        for (Workcenter workcenter : workcentersToDelete) {
            if (workcenter.getIsActive()) {
                throw new RuntimeException("사용 중인 작업장은 삭제할 수 없습니다. ID: " + workcenter.getId());
            }
        }

        // 삭제
        workcenterRepository.deleteAllById(ids);

        // 삭제된 작업장 리스트 반환
        return workcentersToDelete;
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

        // 4. 공장 변경 처리
        if (workcenterDTO.getFactoryCode() != null) {
            // 공장코드 포함 작업장 리스트 조회
            List<Workcenter> workcenters = workcenterRepository.findByFactoryCodeContaining(workcenterDTO.getFactoryCode().getCode());

            // 작업장이 없으면 예외를 던짐
            Workcenter selectedWorkcenter = workcenters.stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode() + "에 해당하는 작업장을 찾을 수 없습니다."));

            // 첫 번째 작업장의 Factory(Warehouse)를 설정
            existingWorkcenter.setFactory(selectedWorkcenter.getFactory());
        }

        // 생산공정 변경 처리
        if (workcenterDTO.getProcessCode() != null) {
            // 공정코드가 포함된 작업장 리스트를 조회
            List<Workcenter> workcenters = workcenterRepository.findByCodeContaining(workcenterDTO.getProcessCode().getCode());

            // 작업장이 없을 경우 예외를 던짐
            Workcenter selectedWorkcenter = workcenters.stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode() + "에 해당하는 작업장을 찾을 수 없습니다."));

            // 첫 번째 작업장의 ProcessDetails를 설정
            existingWorkcenter.setProcessDetails(selectedWorkcenter.getProcessDetails());
        }


        // 5. 작업장 업데이트 후 저장
        return workcenterRepository.save(existingWorkcenter);
    }

}
