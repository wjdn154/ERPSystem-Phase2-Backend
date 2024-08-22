package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
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
    public Optional<EquipmentDataDTO> saveEquipment(EquipmentDataDTO dto) {

        //설비 아이디 중복 확인.
        if(equipmentDataRepository.existsByEquipmentNum(dto.getEquipmentNum())){
            throw new RuntimeException(("이미 존재하는 설비번호입니다." + dto.getEquipmentNum()));
        }
       // dto를 엔티티로 변환함
        EquipmentData equipmentData = equipmentToEntity(dto);

       // 엔티티 저장
        EquipmentData saveEquipment = equipmentDataRepository.save(equipmentData);

       // 엔티티를 dto로 변환하여 반환
        EquipmentDataDTO equipmentDataDTO = equipmentToDTO(saveEquipment);

        return Optional.of(equipmentDataDTO);

    }

    //설비 수정
    @Override
    public Optional<EquipmentDataDTO> updateEquipment(Long id, EquipmentDataDTO dto) {

        //dto를 엔티티로 변환
        EquipmentData equipmentData = equipmentToEntity(dto);
        //id에 해당하는 데이터 조회
        EquipmentData target = equipmentDataRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException(id+"에 해당하는 아이디를 찾을 수 없습니다."));


        return Optional.empty();
    }

    //설비 리스트 조회
    @Override
    public List<ListEquipmentDataDTO> findAllEquipmentDataDetails() {

        return equipmentDataRepository.findAll().stream()
                .map(equipmentData ->
                        new ListEquipmentDataDTO(
                                    equipmentData.getId(),
                                    equipmentData.getEquipmentNum(),
                                    equipmentData.getEquipmentName(),
                                    equipmentData.getModelName(),
                                    equipmentData.getEquipmentType(),
                                    equipmentData.getOperationStatus(),
                                    equipmentData.getFactory().getName(),
                                    equipmentData.getWorkcenter().getName()
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
        EquipmentDataShowDTO equipmentDataShowDTO =equipmentShowToDTO(equipmentDetail);

        return Optional.of(equipmentDataShowDTO);


    }
    //equipmentData 엔티티를 equipmentDataDTO로 변환.
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
        equipmentDataShowDTO.setWorkcenterName(equipmentDetail.getWorkcenter().getName());
        equipmentDataShowDTO.setFactoryName(equipmentDetail.getFactory().getName());
        equipmentDataShowDTO.setEquipmentImg(equipmentDetail.getEquipmentImg());

        return equipmentDataShowDTO;
    }
    //설비 삭제
    @Override
    public void deleteEquipment(Long id) {
        //해당 아이디 가져옴
        EquipmentData equipmentData = equipmentDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디로 설비를 조회할 수 없습니다 : "+id));
        //해당 아이디 설비정보 삭제
        equipmentDataRepository.delete(equipmentData);
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

        Workcenter workcenter = workcenterRepository.findById(dto.getWorkcenterId())
                .orElseThrow(() -> new RuntimeException(dto.getWorkcenterId()+"에 해당하는 아이디를 찾을 수 없습니다"));
        equipmentData.setWorkcenter(workcenter);

        //factory 엔티티로 변환
        Warehouse warehouse = warehouseRepository.findById(dto.getFactoryId())
                .orElseThrow(() -> new RuntimeException(dto.getFactoryId() + "에 해당하는 아이디를 찾을 수 없습니다."));
        equipmentData.setFactory(warehouse);

        equipmentData.setEquipmentImg(dto.getEquipmentImg());
        return equipmentData;
    }

    //equipmentData 엔티티를 equipmentDataDTO로 변환.
    private EquipmentDataDTO equipmentToDTO(EquipmentData equipmentData){

        EquipmentDataDTO equipmentDataDTO = new EquipmentDataDTO();
        equipmentDataDTO.setEquipmentNum(equipmentData.getEquipmentNum());
        equipmentDataDTO.setEquipmentName(equipmentData.getEquipmentName());
        equipmentDataDTO.setEquipmentType(equipmentData.getEquipmentType());
        equipmentDataDTO.setManufacturer(equipmentData.getManufacturer());
        equipmentDataDTO.setModelName(equipmentData.getModelName());
        equipmentDataDTO.setPurchaseDate(equipmentData.getPurchaseDate());
        equipmentDataDTO.setInstallDate(equipmentData.getInstallDate());
        equipmentDataDTO.setOperationStatus(equipmentData.getOperationStatus());
        equipmentDataDTO.setCost(equipmentData.getCost());
        equipmentDataDTO.setWorkcenterId(equipmentData.getWorkcenter().getId());
        equipmentDataDTO.setFactoryId(equipmentData.getFactory().getId());
        equipmentDataDTO.setEquipmentImg(equipmentData.getEquipmentImg());

        return equipmentDataDTO;
    }
}
