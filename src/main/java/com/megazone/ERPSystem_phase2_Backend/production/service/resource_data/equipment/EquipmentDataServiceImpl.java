package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final WorkcenterRepository workcenterRepository;
    private final WarehouseRepository warehouseRepository;

    //설비 등록.저장
    @Override
    public Optional<EquipmentDataShowDTO> saveEquipment(EquipmentDataDTO dto) {

        //설비 아이디 중복 확인.
        if(equipmentDataRepository.existsByEquipmentNum(dto.getEquipmentNum())){
            throw new IllegalArgumentException(("이미 존재하는 설비번호입니다." + dto.getEquipmentNum()));
        }
       // dto를 엔티티로 변환함
        EquipmentData equipmentData = equipmentToEntity(dto);

       // 엔티티 저장
        EquipmentData saveEquipment = equipmentDataRepository.save(equipmentData);

       // 엔티티를 dto로 변환하여 반환
        EquipmentDataShowDTO equipmentDataDTO = equipmentShowToDTO(saveEquipment);

        return Optional.of(equipmentDataDTO);

    }

    //설비 수정
    @Override
    public Optional<EquipmentDataUpdateDTO> updateEquipment(Long id, EquipmentDataUpdateDTO dto) {

        //id에 해당하는 엔티티 데이터 조회
        EquipmentData equipmentData = equipmentDataRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException(id+"에 해당하는 아이디를 찾을 수 없습니다."));

        //새로 들어온 dto를 수정할 id에 해당하는 엔티티에 업데이트
        equipmentData.setEquipmentNum(dto.getEquipmentNum());
        equipmentData.setEquipmentName(dto.getEquipmentName());
        equipmentData.setEquipmentType(dto.getEquipmentType());
        equipmentData.setManufacturer(dto.getManufacturer());
        equipmentData.setModelName(dto.getModelName());
        equipmentData.setInstallDate(dto.getInstallDate());
        equipmentData.setOperationStatus(dto.getOperationStatus());
        equipmentData.setCost(dto.getCost());

        Workcenter workcenter = workcenterRepository.findByCode(dto.getWorkcenterCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다. "));
        equipmentData.setWorkcenter(workcenter);

        Warehouse factory = warehouseRepository.findByCode(dto.getFactoryCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다."));
        equipmentData.setFactory(factory);

        equipmentData.setEquipmentImg(dto.getEquipmentImg());

        //업데이트된 엔티티 저장.
        EquipmentData updatedEquipmentEntity =equipmentDataRepository.save(equipmentData);

        //저장된 엔티티 dto로 변환.
        EquipmentDataUpdateDTO equipmentDataUpdateDTO = equipmentUpdateToDTO(updatedEquipmentEntity);
        return Optional.of(equipmentDataUpdateDTO);
    }

    //설비 리스트 조회
    @Override
    public List<ListEquipmentDataDTO> findAllEquipmentDataDetails(Long companyId) {

        return equipmentDataRepository.findAllByCompanyIdOrderByPurchaseDateDesc(companyId).stream()
                .map(equipmentData -> new ListEquipmentDataDTO(
                                    equipmentData.getId(),
                                    equipmentData.getEquipmentNum(),
                                    equipmentData.getEquipmentName(),
                                    equipmentData.getModelName(),
                                    equipmentData.getEquipmentType(),
                                    equipmentData.getOperationStatus(),
                                    equipmentData.getFactory().getName(),
                                    equipmentData.getWorkcenter().getName(),
                                    equipmentData.getCompany().getId()
                            )
                ).collect(Collectors.toList());
        }

    //설비 상세 조회
    @Override
    public Optional<EquipmentDataShowDTO> findEquipmentDataDetailById(Long id) {

        //엔티티 조회
        EquipmentData equipmentDetail = equipmentDataRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("아이디가 올바르지 않습니다."));

        //엔티티를 dto로 변환.
        EquipmentDataShowDTO equipmentDataShowDTO = equipmentShowToDTO(equipmentDetail);

        return Optional.of(equipmentDataShowDTO);


    }

    //설비 삭제
    @Override
    public void deleteEquipment(Long id) {
        //해당 아이디 가져옴
        EquipmentData equipmentData = equipmentDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 조회할 수 없습니다 : "+id));
        //해당 아이디 설비정보 삭제
        equipmentDataRepository.delete(equipmentData);
    }

    //equipmentData 엔티티를 equipmentDataUpdateDto로 변환
    private EquipmentDataShowDTO equipmentShowToDTO(EquipmentData equipmentDetail){

        EquipmentDataShowDTO equipmentDataShowDTO = new EquipmentDataShowDTO();
        equipmentDataShowDTO.setId(equipmentDetail.getId());
        equipmentDataShowDTO.setEquipmentNum(equipmentDetail.getEquipmentNum());
        equipmentDataShowDTO.setEquipmentName(equipmentDetail.getEquipmentName());
        equipmentDataShowDTO.setEquipmentType(equipmentDetail.getEquipmentType());
        equipmentDataShowDTO.setManufacturer(equipmentDetail.getManufacturer());
        equipmentDataShowDTO.setModelName(equipmentDetail.getModelName());
        equipmentDataShowDTO.setPurchaseDate(equipmentDetail.getPurchaseDate());
        equipmentDataShowDTO.setInstallDate(equipmentDetail.getInstallDate());
        equipmentDataShowDTO.setOperationStatus(equipmentDetail.getOperationStatus());
        equipmentDataShowDTO.setCost(equipmentDetail.getCost());
        equipmentDataShowDTO.setWorkcenterCode(equipmentDetail.getWorkcenter().getCode());
        equipmentDataShowDTO.setWorkcenterName(equipmentDetail.getWorkcenter().getName());
        equipmentDataShowDTO.setFactoryCode(equipmentDetail.getFactory().getCode());
        equipmentDataShowDTO.setFactoryName(equipmentDetail.getFactory().getName());
        equipmentDataShowDTO.setEquipmentImg(equipmentDetail.getEquipmentImg());

        return equipmentDataShowDTO;
    }
    //equipmentData 엔티티를 equipmentDataDTO로 변환.
    private EquipmentDataUpdateDTO equipmentUpdateToDTO(EquipmentData equipmentDetail){

        EquipmentDataUpdateDTO equipmentDataUpdateDTO = new EquipmentDataUpdateDTO();
        equipmentDataUpdateDTO.setId(equipmentDetail.getId());
        equipmentDataUpdateDTO.setEquipmentNum(equipmentDetail.getEquipmentNum());
        equipmentDataUpdateDTO.setEquipmentName(equipmentDetail.getEquipmentName());
        equipmentDataUpdateDTO.setEquipmentType(equipmentDetail.getEquipmentType());
        equipmentDataUpdateDTO.setManufacturer(equipmentDetail.getManufacturer());
        equipmentDataUpdateDTO.setModelName(equipmentDetail.getModelName());
        equipmentDataUpdateDTO.setPurchaseDate(equipmentDetail.getPurchaseDate());
        equipmentDataUpdateDTO.setInstallDate(equipmentDetail.getInstallDate());
        equipmentDataUpdateDTO.setOperationStatus(equipmentDetail.getOperationStatus());
        equipmentDataUpdateDTO.setCost(equipmentDetail.getCost());
        equipmentDataUpdateDTO.setWorkcenterCode(equipmentDetail.getWorkcenter().getCode());
        equipmentDataUpdateDTO.setFactoryCode(equipmentDetail.getFactory().getCode());
        equipmentDataUpdateDTO.setEquipmentImg(equipmentDetail.getEquipmentImg());

        return equipmentDataUpdateDTO;
    }

    //equipmentDataDto를 엔티티로 변환하는 메서드
    private EquipmentData equipmentToEntity(EquipmentDataDTO dto){
        EquipmentData equipmentData = new EquipmentData();
        equipmentData.setEquipmentNum(dto.getEquipmentNum());
        equipmentData.setEquipmentName(dto.getEquipmentName());
        equipmentData.setEquipmentType(dto.getEquipmentType());
        equipmentData.setManufacturer(dto.getManufacturer());
        equipmentData.setModelName(dto.getModelName());
        equipmentData.setPurchaseDate(dto.getPurchaseDate());
        equipmentData.setInstallDate(dto.getInstallDate());
        equipmentData.setOperationStatus(dto.getOperationStatus());
        equipmentData.setCost(dto.getCost());

        //workcenter 엔티티로 변환.<<

        Workcenter workcenter = workcenterRepository.findByCode(dto.getWorkcenterCode())
                .orElseThrow(() -> new RuntimeException(dto.getWorkcenterCode()+"에 해당하는 작업장 코드를 찾을 수 없습니다"));
        equipmentData.setWorkcenter(workcenter);

        //factory 엔티티로 변환
        Warehouse warehouse = warehouseRepository.findByCode(dto.getFactoryCode())
                .orElseThrow(() -> new RuntimeException(dto.getFactoryCode() + "에 해당하는 공장 코드를 찾을 수 없습니다."));
        equipmentData.setFactory(warehouse);

        equipmentData.setEquipmentImg(dto.getEquipmentImg());

        return equipmentData;
    }


}
