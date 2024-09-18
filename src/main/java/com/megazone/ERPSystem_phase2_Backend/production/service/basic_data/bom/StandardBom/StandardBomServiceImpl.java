package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBomMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomMaterialRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.bom.StandardBomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StandardBomServiceImpl implements StandardBomService {

    private final StandardBomRepository standardBomRepository;
    private final StandardBomMaterialRepository standardBomMaterialRepository;


    @Override
    public StandardBom createStandardBom(StandardBom standardBom) {

        if (standardBom.getLossRate() < 0) {
            throw new IllegalArgumentException("손실율은 0 이상이어야 합니다.");
        }

        if (standardBom.getStartDate().isAfter(standardBom.getExpiredDate())) {
            throw new IllegalArgumentException("유효 시작일은 종료일 이전이어야 합니다.");
        }

        StandardBom savedBom = standardBomRepository.save(standardBom);

        // 자식 엔티티에 부모 엔티티 설정 및 저장
        if (standardBom.getBomMaterials() != null) {
            standardBom.getBomMaterials().forEach(material -> {
                material.setBom(savedBom); // 부모와 연관 설정 - bom_id 가 null로 저장되는 일 방지
                standardBomMaterialRepository.save(material); // 자식 엔티티 저장
            });
        }

        return savedBom;
    }



    @Override
    public Optional<StandardBom> getStandardBomById(Long id) {
        return standardBomRepository.findById(id)
                .map(bom -> {
                    // 자재 목록을 함께 조회
                    List<StandardBomMaterial> materials = standardBomMaterialRepository.findByBomId(bom.getId());
                    bom.setBomMaterials(materials);
                    return bom;
                });
    }


    @Override
    public List<StandardBom> getAllStandardBoms() {
        return standardBomRepository.findAll();
    }

    @Override
    public StandardBom updateStandardBom(Long id, StandardBom updatedBom) {
        // 기존 BOM 조회
        StandardBom existingBom = standardBomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM을 찾을 수 없습니다."));

        // 기존 BOM을 새로운 버전으로 복사
        StandardBom newBomVersion = new StandardBom();
        newBomVersion.setCode(existingBom.getCode());
        newBomVersion.setVersion(existingBom.getVersion() + 1); // 버전 1 증가
        newBomVersion.setCreatedDate(LocalDateTime.now());
        newBomVersion.setIsActive(updatedBom.getIsActive());
        newBomVersion.setParentProduct(updatedBom.getParentProduct());
        newBomVersion.setBomMaterials(updatedBom.getBomMaterials()); // 자재 목록 복사

        return standardBomRepository.save(newBomVersion); // 새 버전 저장
    }



    @Override
    public void deleteStandardBom(Long id) {
        StandardBom bom = standardBomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM을 찾을 수 없습니다."));

        // 자재 목록 삭제
        standardBomMaterialRepository.deleteByBomId(bom.getId());

        // BOM 삭제
        standardBomRepository.delete(bom);
    }


    @Override
    public List<StandardBom> getChildBoms(Long parentProductId) {
        return standardBomRepository.findByParentProductId(parentProductId);
    }

    @Override
    public List<StandardBom> getParentBoms(Long childProductId) {
        return standardBomRepository.findByChildProductId(childProductId);
    }

}
