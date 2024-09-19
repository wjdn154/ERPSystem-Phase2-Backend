package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.material;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.HazardousMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListHazardousMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListMaterialDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialDataServiceImpl implements MaterialDataService{

    private final MaterialDataRepository materialDataRepository;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    //자재 리스트 조회
    @Override
    public List<ListMaterialDataDTO> findAllMaterial(Long companyId) {

        return materialDataRepository.findAllByCompanyId(companyId).stream()
                .map(material -> new ListMaterialDataDTO(
                        material.getId(),
                        material.getMaterialCode(),
                        material.getMaterialName(),
                        material.getMaterialType(),
                        material.getStockQuantity(),
                        material.getPurchasePrice(),
                        material.getSupplier().getCode(),
                        material.getSupplier().getRepresentativeName(),
                        (long)material.getHazardousMaterial().size()
                )).collect(Collectors.toList());
    }

    //자재 상세 내용 수정
    @Override
    public Optional<ListMaterialDataDTO> updateMaterial(Long id, ListMaterialDataDTO dto) {

        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다."));

        if(materialDataRepository.existsByMaterialCode(dto.getMaterialCode())){
            throw new IllegalArgumentException(("이미 존재하는 코드 입니다."));
        }

        //새로 들어온 dto 기존 엔티티에 업데이트.
        materialData.setMaterialCode(dto.getMaterialCode());
        materialData.setMaterialName(dto.getMaterialName());
        materialData.setMaterialType(dto.getMaterialType());
        materialData.setStockQuantity(dto.getStockQuantity());
        materialData.setPurchasePrice(dto.getPurchasePrice());

        Client client =clientRepository.findByCode(dto.getMaterialCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 거래처 코드가 없습니다."));

        materialData.setSupplier(client);

        MaterialData updateMaterial = materialDataRepository.save(materialData);

        ListMaterialDataDTO listMaterialDataDTO = materialUpdateDTO(updateMaterial);

        return Optional.of(listMaterialDataDTO);
    }

    //자재 상세내용 등록
    @Override
    public Optional<ListMaterialDataDTO> createMaterial(Long companyId, ListMaterialDataDTO dto) {

        //자재 코드 중복 확인
        if(materialDataRepository.existsByMaterialCode(dto.getMaterialCode())){
            throw new IllegalArgumentException(("이미 존재하는 코드 입니다."));
        }
       //dto를 엔티티로 변환
        MaterialData materialData = materialToEntity(companyId,dto);

        //엔티티 저장
        MaterialData createMaterial = materialDataRepository.save(materialData);

        //엔티티를 dto로 변환
        ListMaterialDataDTO listMaterialDataDTO = materialUpdateDTO(createMaterial);

        return Optional.of(listMaterialDataDTO);
    }
    
    //자재 상세 내용 삭제
    @Override
    public void deleteMaterial(Long id) {

        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 조회할 수 없습니다."));

        //해당 아이디 자재 삭제
        materialDataRepository.delete(materialData);
    }

    //해당 자재의 유해물질 리스트 조회
    @Override
    public ListHazardousMaterialDTO findAllHazardousMaterialById(Long id) {
        
        //자재 아이디로 자재 조회
        MaterialData material = materialDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재를 조회할 수 없습니다."));

        //유해물질 리스트 생성
        List<HazardousMaterialDTO> hazardousMaterialList = material.getHazardousMaterial().stream()
                .map(hazardousMaterial -> new HazardousMaterialDTO(
                        hazardousMaterial.getHazardousMaterialCode(),
                        hazardousMaterial.getHazardousMaterialName(),
                        hazardousMaterial.getHazardLevel(),
                        hazardousMaterial.getDescription()
                )).toList();

        return new ListHazardousMaterialDTO(
                material.getId(),
                material.getMaterialCode(),
                material.getMaterialName(),
                hazardousMaterialList
        );
    }
    //해당 자재의 품목 리스트 조회
    @Override
    public ListProductMaterialDTO findAllProductMaterialById(Long id) {

        //자재 아이디로 자재 조회
        MaterialData material = materialDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재를 조회할 수 없습니다."));

        //품목 리스트 생성
        List<ProductMaterialDTO> productMaterialList = material.getProduct().stream()
                .map(product -> new ProductMaterialDTO(
                        product.getId(),
                        product.getCode(),
                        product.getName(),
                        product.getProductGroup().getName()
                )).toList();

        return new ListProductMaterialDTO(
                material.getId(),
                material.getMaterialCode(),
                material.getMaterialName(),
                productMaterialList
        );
    }
    
    //ListMaterialDataDTO 를 엔티티로 변환
    private MaterialData materialToEntity(Long companyId, ListMaterialDataDTO dto) {

        MaterialData materialData = new MaterialData();
        materialData.setMaterialCode(dto.getMaterialCode());
        materialData.setMaterialName(dto.getMaterialName());
        materialData.setMaterialType(dto.getMaterialType());
        materialData.setStockQuantity(dto.getStockQuantity());
        materialData.setPurchasePrice(dto.getPurchasePrice());

        Client client =clientRepository.findByCode(dto.getMaterialCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 거래처 코드가 없습니다."));

        materialData.setSupplier(client);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회사 아이디가 존재하지 않습니다."));

        materialData.setCompany(company);

        return materialData;
    }

    //자재 엔티티를 listMaterialDTO 로 변환
    private ListMaterialDataDTO materialUpdateDTO(MaterialData updateMaterial) {

        ListMaterialDataDTO dto = new ListMaterialDataDTO();
        dto.setId(updateMaterial.getId());
        dto.setMaterialCode(updateMaterial.getMaterialCode());
        dto.setMaterialName(updateMaterial.getMaterialName());
        dto.setMaterialType(updateMaterial.getMaterialType());
        dto.setStockQuantity(updateMaterial.getStockQuantity());
        dto.setPurchasePrice(updateMaterial.getPurchasePrice());
        dto.setRepresentativeCode(updateMaterial.getSupplier().getCode());
        dto.setRepresentativeName(updateMaterial.getSupplier().getRepresentativeName());
        dto.setHazardousMaterialQuantity((long)updateMaterial.getHazardousMaterial().size());

        return dto;
    }
}
