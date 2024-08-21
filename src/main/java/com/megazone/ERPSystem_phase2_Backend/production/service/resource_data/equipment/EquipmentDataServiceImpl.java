package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentDataServiceImpl implements EquipmentDataService{

    private final EquipmentDataRepository equipmentDataRepository;

    @Override
    public Optional<EquipmentDataDTO> saveEquipment(EquipmentDataDTO dto) {

        //dto를 엔티티로 변환함
        //EquipmentData equipmentData = dto.toEntity(workcenter);
        //엔티티 저장
        //EquipmentData saveEquipment = equipmentDataRepository.save(equipmentData);

        //엔티티를 dto로 변환하여 반환
//        EquipmentDataDTO equipmentDataDTO = new EquipmentDataDTO(
//
//                saveEquipment.getEquipmentNum(),
//                saveEquipment.getEquipmentName(),
//                saveEquipment.getEquipmentType(),
//                saveEquipment.getManufacturer(),
//                saveEquipment.getModelName(),
//                saveEquipment.getPurchaseDate(),
//                saveEquipment.getInstallDate(),
//                saveEquipment.getOperationStatus(),
//                saveEquipment.getCost(),
//                saveEquipment.getWorkcenter(),
//                saveEquipment.getEquipmentImg()
//        );
        return null;

    }

    @Override
    public Optional<EquipmentDataDTO> updateEquipment(Long id, EquipmentDataDTO dto) {

        return Optional.empty();
    }


    @Override
    public List<ListEquipmentDataDTO> findAllEquipmentDataDetails() {

        return equipmentDataRepository.findAll().stream()
                .map(equipmentData ->
                        {
                            WarehouseDTO warehouseDTO = new WarehouseDTO(
                                    equipmentData.getFactory().getId(),
                                    equipmentData.getFactory().getCode(),
                                    equipmentData.getFactory().getName(),
                                    equipmentData.getFactory().getWarehouseType(),
                                    equipmentData.getFactory().getProductionProcess(),
                                    equipmentData.getFactory().getIsActive()// <<<<  이런식으로 넣을 DTO를 새로만들어

                    );
                        return new ListEquipmentDataDTO(
                                    equipmentData.getId(),
                                    equipmentData.getEquipmentNum(),
                                    equipmentData.getEquipmentName(),
                                    equipmentData.getModelName(),
                                    equipmentData.getEquipmentType(),
                                    equipmentData.getOperationStatus(),
                                    warehouseDTO
                            ); // 그래서 만든 얘를 넣어
                        }
                ).collect(Collectors.toList());
        }

    @Override
    public Optional<EquipmentDataDTO> findEquipmentDataDetailById(Long id) {

        return null;
    }

    @Override
    public void deleteEquipment(Long id) {
        //해당 아이디 가져옴
        EquipmentData equipmentData = equipmentDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디로 설비를 조회할 수 없습니다 : "+id));
        //해당 아이디 설비정보 삭제
        equipmentDataRepository.delete(equipmentData);
    }
}
