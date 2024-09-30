package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.HazardousMaterial;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.HazardousMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.HazardousMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.HazardousMaterial.HazardousMaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HazardousMaterialServiceImpl implements HazardousMaterialService{

    private final HazardousMaterialRepository hazardousMaterialRepository;
    private final CompanyRepository companyRepository;

    //유해물질 리스트 조회
    @Override
    public List<HazardousMaterialDTO> findAllHazardousMaterial() {

        return hazardousMaterialRepository.findAll().stream()
                .map(hazardousMaterial -> HazardousMaterialDTO.builder()  //빌더패턴 사용
                        .id(hazardousMaterial.getId())
                        .hazardousMaterialCode(hazardousMaterial.getHazardousMaterialCode())
                        .hazardousMaterialName(hazardousMaterial.getHazardousMaterialName())
                        .hazardLevel(hazardousMaterial.getHazardLevel())
                        .description(hazardousMaterial.getDescription())
                        .build()
                ).collect(Collectors.toList());
    }

    //유해물질 등록
    @Override
    public Optional<HazardousMaterialDTO> createHazardousMaterial(HazardousMaterialDTO dto) {

        //유해물질 코드 중복 확인
        if(hazardousMaterialRepository.existsByHazardousMaterialCode(dto.getHazardousMaterialCode())){
            throw new IllegalArgumentException(("이미 존재하는 코드입니다."));
        }
        //dto를 엔티티로 변환
        HazardousMaterial hazardousMaterial = hazardousMaterialToEntity(dto);

        HazardousMaterial createHazardousMaterial = hazardousMaterialRepository.save(hazardousMaterial);

        //엔티티를 dto로 변환.
        HazardousMaterialDTO hazardousMaterialDTO = hazardousMaterialToDTO(createHazardousMaterial);

        return Optional.of(hazardousMaterialDTO);
    }

    //유해물질 수정
    @Override
    public Optional<HazardousMaterialDTO> updateHazardousMaterial(Long id, HazardousMaterialDTO dto) {

        //아이디 존재 여부 확인
        HazardousMaterial hazardousMaterial = hazardousMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 존재하지 않습니다."));

        // 중복된 HazardousMaterialCode 체크 (자신의 HazardousMaterialCode는 제외)
        if (hazardousMaterialRepository.existsByHazardousMaterialCodeAndIdNot(dto.getHazardousMaterialCode(), id)) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다.");
        }

        //dto를 기존 엔티티에 업데이트
        hazardousMaterial.setHazardousMaterialCode(dto.getHazardousMaterialCode());
        hazardousMaterial.setHazardousMaterialName(dto.getHazardousMaterialName());
        hazardousMaterial.setHazardLevel(dto.getHazardLevel());
        hazardousMaterial.setDescription(dto.getDescription());

        HazardousMaterial updateHazardousMaterial = hazardousMaterialRepository.save(hazardousMaterial);

        //엔티를 dto로 변환
       HazardousMaterialDTO hazardousMaterialDTO = hazardousMaterialToDTO(updateHazardousMaterial);

       return Optional.of(hazardousMaterialDTO);
    }

    //유해물질 삭제
    @Override
    public void deleteHazardousMaterial(Long id) {

        HazardousMaterial hazardousMaterial = hazardousMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 존재하지 않습니다."));

        //해당 아이디 유해물질 정보 삭제
        hazardousMaterialRepository.delete(hazardousMaterial);

    }

    //엔티티를 dto로 변환
    private HazardousMaterialDTO hazardousMaterialToDTO(HazardousMaterial hazardousMaterial) {

        HazardousMaterialDTO hazardousMaterialDto = HazardousMaterialDTO.builder()
                .id(hazardousMaterial.getId())
                .hazardousMaterialCode(hazardousMaterial.getHazardousMaterialCode())
                .hazardousMaterialName(hazardousMaterial.getHazardousMaterialName())
                .hazardLevel(hazardousMaterial.getHazardLevel())
                .description(hazardousMaterial.getDescription())
                .build();
        return hazardousMaterialDto;
    }

    //dto 를 엔티티로 변환
    private HazardousMaterial hazardousMaterialToEntity(HazardousMaterialDTO dto) {

        HazardousMaterial hazardousMaterial = new HazardousMaterial();
        hazardousMaterial.setHazardousMaterialCode(dto.getHazardousMaterialCode());
        hazardousMaterial.setHazardousMaterialName(dto.getHazardousMaterialName());
        hazardousMaterial.setHazardLevel(dto.getHazardLevel());
        hazardousMaterial.setDescription(dto.getDescription());

        return hazardousMaterial;
    }
}
