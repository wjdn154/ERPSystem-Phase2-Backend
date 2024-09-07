package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.MaintenanceHistory;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListMaintenanceHistoryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.MaintenanceHistoryDetailShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.MaintenanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MaintenanceHistoryServiceImpl implements MaintenanceHistoryService{

    private final MaintenanceHistoryRepository maintenanceHistoryRepository;
    private final EquipmentDataRepository equipmentDataRepository;
    private final WorkcenterRepository workcenterRepository;
    private final WarehouseRepository warehouseRepository;
    //유지보수 이력 리스트 조회
    @Override
    public List<ListMaintenanceHistoryDTO> findAllMaintenanceHistory(Long companyId) {

        return maintenanceHistoryRepository.findAllByCompanyIdOrderByMaintenanceDateDesc(companyId).stream()
                .map(maintenanceHistory -> new ListMaintenanceHistoryDTO(
                        maintenanceHistory.getId(),
                        maintenanceHistory.getEquipment().getEquipmentName(),
                        maintenanceHistory.getMaintenanceManager(),
                        maintenanceHistory.getMaintenanceType(),
                        maintenanceHistory.getMaintenanceStatus(),
                        maintenanceHistory.getMaintenanceDate(),
                        maintenanceHistory.getEquipment().getWorkcenter().getName(),
                        maintenanceHistory.getEquipment().getFactory().getName()
                ))
                .collect(Collectors.toList());
    }

    //유지보수 이력 상세 조회
    @Override
    public Optional<MaintenanceHistoryDetailShowDTO> findMaintenanceHistoryById(Long id) {

        //해당 아이디에 해당하는 유지보수 이력 엔티티 조회
        MaintenanceHistory maintenanceHistory = maintenanceHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디를 조회할 수 없습니다." + id));

        //엔티티를 dto로 변환
        MaintenanceHistoryDetailShowDTO maintenanceHistoryDetailShowDTO = maintenanceToShowDTO(maintenanceHistory);
       //dto 반환
        return Optional.of(maintenanceHistoryDetailShowDTO);
    }

    //유지보수 이력 상세 등록
    @Override
    public Optional<MaintenanceHistoryDetailShowDTO> saveMaintenanceHistory(MaintenanceHistoryDetailDTO dto) {

        //dto를 엔티티로 변환
        MaintenanceHistory maintenanceHistory = maintenanceHistoryToEntity(dto);

        //엔티티 저장
        MaintenanceHistory savedMaintenanceHistory = maintenanceHistoryRepository.save(maintenanceHistory);

        //엔티티를 DTO로 변환하여 반환
        MaintenanceHistoryDetailShowDTO maintenanceHistoryDTO = maintenanceToShowDTO(savedMaintenanceHistory);


        return Optional.of(maintenanceHistoryDTO);
    }

    //유지보수 이력 상세 수정
    @Override
    public Optional<MaintenanceHistoryDetailShowDTO> updateMaintenanceHistory(Long id, MaintenanceHistoryDetailShowDTO dto) {

        //id에 해당하는 엔티티 데이터 조회
        MaintenanceHistory maintenanceHistory = maintenanceHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"에 해당하는 아이디를 조회할 수 없습니다."));

        //새로 들어온 dto를 기존 엔티티에 업데이트
        maintenanceHistory.setMaintenanceManager(dto.getMaintenanceManager());
        maintenanceHistory.setMaintenanceDate(dto.getMaintenanceDate());
        maintenanceHistory.setMaintenanceType(dto.getMaintenanceType());
        maintenanceHistory.setTitle(dto.getTitle());
        maintenanceHistory.setMaintenanceDetail(dto.getMaintenanceDetail());
        maintenanceHistory.setMaintenanceCost(dto.getMaintenanceCost());
        maintenanceHistory.setMaintenanceStatus(dto.getMaintenanceStatus());
        maintenanceHistory.setNextMaintenanceDate(dto.getNextScheduleDate());

        EquipmentData equipmentData = equipmentDataRepository.findByEquipmentNum(dto.getEquipmentNum())
                        .orElseThrow(() -> new IllegalArgumentException("해당 설비번호를 조회할 수 없습니다."));
        maintenanceHistory.setEquipment(equipmentData);

        //업데이트된 엔티티 저장
        MaintenanceHistory updateMaintenanceHistory = maintenanceHistoryRepository.save(maintenanceHistory);

        //저장된 엔티티 dto로 변환
        MaintenanceHistoryDetailShowDTO MaintenanceHistoryUpdateDetailDTO = maintenanceToShowDTO(updateMaintenanceHistory);

        return Optional.of(MaintenanceHistoryUpdateDetailDTO);
    }


    //유지보수 이력 상세 삭제
    @Override
    public void deleteMaintenanceHistory(Long id) {

        //해당 아이디 가져옴
        MaintenanceHistory maintenanceHistory = maintenanceHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 조회할 수 없습니다."+id));
        //해당 아이디 유지보수 이력 상세 삭제
        maintenanceHistoryRepository.delete(maintenanceHistory);
    }


    //maintenanceHistory 엔티티를 maintenanceHistoryShowDTO로 변환
    private MaintenanceHistoryDetailShowDTO maintenanceToShowDTO(MaintenanceHistory maintenanceHistory) {

        MaintenanceHistoryDetailShowDTO maintenanceHistoryDetailShowDTO = new MaintenanceHistoryDetailShowDTO();

        maintenanceHistoryDetailShowDTO.setId(maintenanceHistory.getId());
        maintenanceHistoryDetailShowDTO.setEquipmentNum(maintenanceHistory.getEquipment().getEquipmentNum());
        maintenanceHistoryDetailShowDTO.setEquipmentName(maintenanceHistory.getEquipment().getEquipmentName());
        maintenanceHistoryDetailShowDTO.setMaintenanceManager(maintenanceHistory.getMaintenanceManager());
        maintenanceHistoryDetailShowDTO.setMaintenanceType(maintenanceHistory.getMaintenanceType());
        maintenanceHistoryDetailShowDTO.setMaintenanceCost(maintenanceHistory.getMaintenanceCost());
        maintenanceHistoryDetailShowDTO.setMaintenanceStatus(maintenanceHistory.getMaintenanceStatus());
        maintenanceHistoryDetailShowDTO.setMaintenanceDate(maintenanceHistory.getMaintenanceDate());
        maintenanceHistoryDetailShowDTO.setNextScheduleDate(maintenanceHistory.getNextMaintenanceDate());
        maintenanceHistoryDetailShowDTO.setWorkcenterCode(maintenanceHistory.getEquipment().getWorkcenter().getCode());
        maintenanceHistoryDetailShowDTO.setWorkcenterName(maintenanceHistory.getEquipment().getWorkcenter().getName());
        maintenanceHistoryDetailShowDTO.setFactoryCode(maintenanceHistory.getEquipment().getFactory().getCode());
        maintenanceHistoryDetailShowDTO.setFactoryName(maintenanceHistory.getEquipment().getFactory().getName());
        maintenanceHistoryDetailShowDTO.setTitle(maintenanceHistory.getTitle());
        maintenanceHistoryDetailShowDTO.setMaintenanceDetail(maintenanceHistory.getMaintenanceDetail());

        return maintenanceHistoryDetailShowDTO;
    }

    //maintenanceHistoryDetailDTO를 maintenanceHistory 엔티티로 변환
    private MaintenanceHistory maintenanceHistoryToEntity(MaintenanceHistoryDetailDTO dto) {
        MaintenanceHistory maintenanceHistory = new MaintenanceHistory();

        maintenanceHistory.setMaintenanceManager(dto.getMaintenanceManager());
        maintenanceHistory.setMaintenanceDate(dto.getMaintenanceDate());
        maintenanceHistory.setMaintenanceType(dto.getMaintenanceType());
        maintenanceHistory.setMaintenanceDetail(dto.getMaintenanceDetail());
        maintenanceHistory.setMaintenanceCost(dto.getMaintenanceCost());
        maintenanceHistory.setMaintenanceStatus(dto.getMaintenanceStatus());
        maintenanceHistory.setNextMaintenanceDate(dto.getNextScheduleDate());
        maintenanceHistory.setMaintenanceDetail(dto.getMaintenanceDetail());
        maintenanceHistory.setTitle(dto.getTitle());

        EquipmentData equipmentData = equipmentDataRepository.findByEquipmentNum(dto.getEquipmentNum())
                .orElseThrow(() -> new IllegalArgumentException("해당 설비번호를 조회할 수 없습니다."));
        maintenanceHistory.setEquipment(equipmentData);

        return maintenanceHistory;
    }
}
