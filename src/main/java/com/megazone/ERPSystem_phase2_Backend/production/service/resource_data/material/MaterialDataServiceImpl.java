package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.material;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.HazardousMaterial;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialHazardous;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.*;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.HazardousMaterial.HazardousMaterialRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.MaterialHazardous.MaterialHazardousRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialProduct.MaterialProductRepository;
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
    private final HazardousMaterialRepository hazardousMaterialRepository;
    private final ProductRepository productRepository;
    private final MaterialProductRepository materialProductRepository;
    private final MaterialHazardousRepository materialHazardousRepository;
    //자재 리스트 조회
    @Override
    public List<ListMaterialDataDTO> findAllMaterial() {

        return materialDataRepository.findAll().stream()
                .map(material -> new ListMaterialDataDTO(
                        material.getId(),
                        material.getMaterialCode(),
                        material.getMaterialName(),
                        material.getMaterialType(),
                        material.getStockQuantity(),
                        material.getPurchasePrice(),
                        material.getSupplier().getCode(),
                        material.getSupplier().getRepresentativeName(),
                        (long)material.getMaterialHazardous().size()
                )).collect(Collectors.toList());
    }

    //자재 상세 조회
    @Override
    public Optional<MaterialDataShowDTO> findMaterialById(Long id) {

        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다."+id));

        //엔티티를 materialDataShowDTO로 변환
        MaterialDataShowDTO materialDataShowDTO = materialCreateDTO(materialData);

        return Optional.of(materialDataShowDTO);
    }

    //자재 리스트 수정
    @Override
    public Optional<ListMaterialDataDTO> updateMaterial(Long id, ListMaterialDataDTO dto) {

        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다."+id));

        // 중복된 material_code 체크 (자신의 material_code는 제외)
        if (materialDataRepository.existsByMaterialCodeAndIdNot(dto.getMaterialCode(), id)) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다.");
        }

        //새로 들어온 dto 기존 엔티티에 업데이트.
        materialData.setMaterialCode(dto.getMaterialCode());
        materialData.setMaterialName(dto.getMaterialName());
        materialData.setMaterialType(dto.getMaterialType());
        materialData.setStockQuantity(dto.getStockQuantity());
        materialData.setPurchasePrice(dto.getPurchasePrice());

        Client client =clientRepository.findByCode(dto.getRepresentativeCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 거래처 코드가 없습니다."));

        materialData.setSupplier(client);

        MaterialData updateMaterial = materialDataRepository.save(materialData);

        ListMaterialDataDTO listMaterialDataDTO = materialUpdateDTO(updateMaterial);

        return Optional.of(listMaterialDataDTO);
    }

    //자재 상세내용 등록
    @Override
    public Optional<MaterialDataShowDTO> createMaterial(MaterialDataShowDTO dto) {

        //자재 코드 중복 확인
        if(materialDataRepository.existsByMaterialCode(dto.getMaterialCode())){
            throw new IllegalArgumentException(("이미 존재하는 코드 입니다."));
        }
       //dto를 엔티티로 변환
        MaterialData materialData = materialToEntity(dto);

        //엔티티 저장
        MaterialData createMaterial = materialDataRepository.save(materialData);

        //엔티티를 dto로 변환
        MaterialDataShowDTO materialDataShowDTO = materialCreateDTO(createMaterial);

        return Optional.of(materialDataShowDTO);
    }
    
    //자재 상세 내용 삭제
    @Override
    public void deleteMaterial(Long id) {

        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 조회할 수 없습니다."));

        //자재와 연관된 유해물질 관계 제거
        List<MaterialHazardous> materialHazardousList = materialData.getMaterialHazardous();
        for (MaterialHazardous materialHazardous : materialHazardousList) {

            //중간 엔티티에서 관계 제거
            materialHazardous.setMaterialData(null);
            //중간 엔티티 삭제
            materialHazardousRepository.delete(materialHazardous);
        }

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
        List<HazardousMaterialDTO> hazardousMaterialList = material.getMaterialHazardous().stream()
                .map(hazardousMaterial -> new HazardousMaterialDTO(
                        hazardousMaterial.getId(),
                        hazardousMaterial.getHazardousMaterial().getHazardousMaterialCode(),
                        hazardousMaterial.getHazardousMaterial().getHazardousMaterialName(),
                        hazardousMaterial.getHazardousMaterial().getHazardLevel(),
                        hazardousMaterial.getHazardousMaterial().getDescription()
                )).collect(Collectors.toList());

        return new ListHazardousMaterialDTO(
                material.getId(),
                material.getMaterialCode(),
                material.getMaterialName(),
                hazardousMaterialList
        );
    }

    //해당 자재의 유해물질 목록 추가
    @Override
    public Optional<ListHazardousMaterialDTO> addHazardousMaterial(Long id, List<HazardousMaterialDTO> hazardousMaterialDTOs) {

        //자재 아이디로 조회
        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재가 존재하지 않습니다."));

        //유해물질 리스트 조회 및 추가, dto 리스트를 엔티티 리스트로 변환
        List<HazardousMaterial> newHazardousMaterials = hazardousMaterialDTOs.stream()
                .map(dto -> hazardousMaterialRepository.findByHazardousMaterialCode(dto.getHazardousMaterialCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 유해물질이 존재하지 않습니다: " + dto.getHazardousMaterialCode())
                        )).collect(Collectors.toList());

        //기존 자재의 유해물질 목록 가져오기
        List<HazardousMaterial> existingHazardousMaterials = materialData.getMaterialHazardous().stream()
                .map(MaterialHazardous::getHazardousMaterial)
                .collect(Collectors.toList());

        //중복되지 않는 유해물질만 추가하기 위해 필터링
        List<HazardousMaterial> filteredNewHazardousMaterials = newHazardousMaterials.stream()
                .filter(hazardousMaterial -> !existingHazardousMaterials.contains(hazardousMaterial))
                .collect(Collectors.toList());

        //중복된 것이 없을 때만 새로 추가
        if(!filteredNewHazardousMaterials.isEmpty()) {
            //기존 자재의 유해물질 중간 엔티티 리스트에 새로 추가할 유해물질을 더함.
            List<MaterialHazardous> materialHazardousList = filteredNewHazardousMaterials.stream()
                    .map(hazardousMaterial -> {
                        MaterialHazardous materialHazardous = new MaterialHazardous();
                        materialHazardous.setMaterialData(materialData);
                        materialHazardous.setHazardousMaterial(hazardousMaterial);
                        return materialHazardous;
                    }).collect(Collectors.toList());

            //중간 엔티티 리스트에 저장. 자바 객체 내부에서 리스트에 엔티티 객체를 추가. db와 관계 없음.
            materialData.getMaterialHazardous().addAll(materialHazardousList);

            //중간 엔티티 저장. db에 실제로 저장.
            materialHazardousRepository.saveAll(materialHazardousList);

            //자재 저장
            materialDataRepository.save(materialData);
        }
        //유해물질 리스트를 dto 리스트로 변환
        List<HazardousMaterialDTO> hazardousMaterialDTOList = materialData.getMaterialHazardous().stream()
                .map(entity -> new HazardousMaterialDTO(
                        entity.getId(),
                        entity.getHazardousMaterial().getHazardousMaterialCode(),
                        entity.getHazardousMaterial().getHazardousMaterialName(),
                        entity.getHazardousMaterial().getHazardLevel(),
                        entity.getHazardousMaterial().getDescription()
                )).collect(Collectors.toList());

        ListHazardousMaterialDTO listHazardousMaterialDTO = new ListHazardousMaterialDTO(
                materialData.getId(),
                materialData.getMaterialCode(),
                materialData.getMaterialName(),
                hazardousMaterialDTOList
        );

        return Optional.of(listHazardousMaterialDTO);
    }

    //해당 자재의 유해물질 목록 제거
    @Override
    public void deleteHazardousMaterial(Long materialId, Long hazardousMaterialId) {

        //자재 아이디로 조회
        MaterialData materialData = materialDataRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재가 존재하지 않습니다."));

        //유해물질 조회
        HazardousMaterial hazardousMaterial = hazardousMaterialRepository.findById(hazardousMaterialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유해물질이 존재하지 않습니다."));

        //중간 엔티티에서 해당 자재와 품목의 관계 제거
        List<MaterialHazardous> materialHazardous = materialData.getMaterialHazardous();
        MaterialHazardous materialHazardousToRemove = materialHazardous.stream()
                .filter(mh -> mh.getHazardousMaterial().getId().equals(hazardousMaterialId))
                .findFirst()  //조건에 맞는 첫번째 요소를 찾음.
                .orElseThrow(() -> new IllegalArgumentException("유해물질이 자재에 존재하지 않습니다."));

       //중간 엔티티에서 제거
       materialHazardous.remove(materialHazardousToRemove);

        //자재 저장
        materialDataRepository.save(materialData);

    }

    //해당 자재의 품목 리스트 조회
    @Override
    public ListProductMaterialDTO findAllProductMaterialById(Long id) {

        //자재 아이디로 자재 조회
        MaterialData material = materialDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재를 조회할 수 없습니다."));

        //품목 리스트 생성
        List<ProductMaterialDTO> productMaterialList = material.getMaterialProducts().stream()
                .map(product -> new ProductMaterialDTO(
                        product.getId(),
                        product.getProduct().getCode(),
                        product.getProduct().getName(),
                        product.getProduct().getProductGroup().getName()
                )).collect(Collectors.toList());

        return new ListProductMaterialDTO(
                material.getId(),
                material.getMaterialCode(),
                material.getMaterialName(),
                productMaterialList
        );
    }

    //해당 자재의 품목 목록 추가
    @Override
    public Optional<ListProductMaterialDTO> addProductMaterial(Long id, List<ProductMaterialDTO> productMaterialDTOs) {

        //자재 아이디 조회
        MaterialData materialData = materialDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재가 존재하지 않습니다."));

        //품목 리스트 조회 및 추가 dto 리스트를 엔티티 리스트로 변환
        List<Product> products = productMaterialDTOs.stream()
                .map(dto -> productRepository.findByCode(dto.getProductCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 품목이 존재하지 않습니다.")))
        .collect(Collectors.toList());

        //기존 자재의 품목 목록 가져오기
        List<Product> existingProducts = materialData.getMaterialProducts().stream()
                .map(MaterialProduct::getProduct)
                .collect(Collectors.toList());

        //중복되지 않는 품목만 추가하기 위해 필터링
        List<Product> filterNewProducts = products.stream()
                .filter(product -> !existingProducts.contains(product))
                .collect(Collectors.toList());

        //중복된 것이 없을 때만 새로 추가
        if(!filterNewProducts.isEmpty()) {

            //기존 자재의 품목 중간 엔티티 리스트에 새로 추가할 품목을 더함.
            List<MaterialProduct> materialProductList = filterNewProducts.stream()
                    .map(product -> {
                        MaterialProduct materialProduct = new MaterialProduct();
                        materialProduct.setMaterialData(materialData);
                        materialProduct.setProduct(product);
                        return materialProduct;
                    }).collect(Collectors.toList());

            //중간 엔티티 리스트에 추가
            materialData.getMaterialProducts().addAll(materialProductList);

            //중간 엔티티 저장
            materialProductRepository.saveAll(materialProductList);

            //자재 저장
            materialDataRepository.save(materialData);
        }
        //전체 품목 리스트를 dto 리스트로 변환
        List<ProductMaterialDTO> productMaterialDTOList = materialData.getMaterialProducts().stream()
                .map(entity -> new ProductMaterialDTO(
                        entity.getId(),
                        entity.getProduct().getCode(),
                        entity.getProduct().getName(),
                        entity.getProduct().getProductGroup().getName()
                )).collect(Collectors.toList());

        ListProductMaterialDTO listProductMaterialDTO = new ListProductMaterialDTO(
                materialData.getId(),
                materialData.getMaterialCode(),
                materialData.getMaterialName(),
                productMaterialDTOList
        );

        return Optional.of(listProductMaterialDTO);
    }

    //해당 자재의 품목 목록 제거
    @Override
    public void deleteProductMaterial(Long materialId, String productCode) {

        //자재 아이디 조회
        MaterialData materialData = materialDataRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자재가 존재하지 않습니다."));

        //품목 아이디 조회
        Product product = productRepository.findByCode(productCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 품목이 존재하지 않습니다."));

        //중간 엔티티에서 해당 자재와 품목의 관계 제거
        List<MaterialProduct> materialProductList = materialData.getMaterialProducts();
        MaterialProduct materialProductToRemove = materialProductList.stream()
                        .filter(mp -> mp.getProduct().getCode().equals(productCode))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("품목이 자재에 존재하지 않습니다."));

        //중간 엔티티에서 제거
        materialProductList.remove(materialProductToRemove);
        
        //중간엔티티 저장
        materialProductRepository.saveAll(materialProductList);

        //자재 저장
        materialDataRepository.save(materialData);
    }

    //MaterialDataShowDTO 를 엔티티로 변환
    private MaterialData materialToEntity(MaterialDataShowDTO dto) {

        MaterialData materialData = new MaterialData();
        materialData.setMaterialCode(dto.getMaterialCode());
        materialData.setMaterialName(dto.getMaterialName());
        materialData.setMaterialType(dto.getMaterialType());
        materialData.setStockQuantity(dto.getStockQuantity());
        materialData.setPurchasePrice(dto.getPurchasePrice());

        Client client =clientRepository.findByCode(dto.getRepresentativeCode())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 거래처 코드가 없습니다."));
        materialData.setSupplier(client);

        //자재 엔티티 먼저 저장(id 자동 생성)
        MaterialData saveMaterialData = materialDataRepository.save(materialData);

        List<HazardousMaterial> hazardousMaterials = dto.getHazardousMaterial().stream()
                .map(hazardousMaterialDTO -> hazardousMaterialRepository.findByHazardousMaterialCode(hazardousMaterialDTO.getHazardousMaterialCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 유해물질 코드가 존재하지 않습니다." + hazardousMaterialDTO.getHazardousMaterialCode())))
                .collect(Collectors.toList());

        //유해물질 중간 엔티티 설정.
        List<MaterialHazardous> materialHazardousList = hazardousMaterials.stream()
                .map(hazardousMaterial -> {
                    MaterialHazardous materialHazardous = new MaterialHazardous();
                    materialHazardous.setMaterialData(saveMaterialData);
                    materialHazardous.setHazardousMaterial(hazardousMaterial);
                    return materialHazardous;
                })
                .collect(Collectors.toList());

        //명시적으로 중간 엔티티 저장
        materialHazardousRepository.saveAll(materialHazardousList);

        List<Product> productMaterials  = dto.getProduct().stream()
                .map(productMaterialDTO -> productRepository.findByCode(productMaterialDTO.getProductCode())
                        .orElseThrow(() -> new IllegalArgumentException("해당 품목 코드가 존재하지 않습니다." + productMaterialDTO.getProductCode())))
                .collect(Collectors.toList());

        //품목 중간 엔티티 설정
        List<MaterialProduct> materialProductList = productMaterials.stream()
                .map(product -> {
                    MaterialProduct materialProduct = new MaterialProduct();
                    materialProduct.setMaterialData(saveMaterialData);
                    materialProduct.setProduct(product);
                    return materialProduct;
                }).collect(Collectors.toList());

        //명시적으로 중간 엔티티 저장
        materialProductRepository.saveAll(materialProductList);

        saveMaterialData.setMaterialHazardous(materialHazardousList);
        saveMaterialData.setMaterialProducts(materialProductList);

        //최종적으로 모든 연관된 엔티티 저장
        materialDataRepository.save(saveMaterialData);

        return saveMaterialData;
    }

    //자재 엔티티를 MaterialDataShowDTO로 변환
    private MaterialDataShowDTO materialCreateDTO(MaterialData createMaterial){

        MaterialDataShowDTO dto = new MaterialDataShowDTO();
        dto.setId(createMaterial.getId());
        dto.setMaterialCode(createMaterial.getMaterialCode());
        dto.setMaterialName(createMaterial.getMaterialName());
        dto.setMaterialType(createMaterial.getMaterialType());
        dto.setStockQuantity(createMaterial.getStockQuantity());
        dto.setPurchasePrice(createMaterial.getPurchasePrice());
        dto.setRepresentativeCode(createMaterial.getSupplier().getCode());
        dto.setRepresentativeName(createMaterial.getSupplier().getRepresentativeName());

        //품목 리스트 생성
        List<ProductMaterialDTO> productMaterialList = createMaterial.getMaterialProducts().stream()
                .map(product -> new ProductMaterialDTO(
                        product.getId(),
                        product.getProduct().getCode(),
                        product.getProduct().getName(),
                        product.getProduct().getProductGroup().getName()
                )).collect(Collectors.toList());

        //유해물질 리스트 생성
        List<HazardousMaterialDTO> hazardousMaterialList = createMaterial.getMaterialHazardous().stream()
                .map(hazardousMaterial -> new HazardousMaterialDTO(
                        hazardousMaterial.getId(),
                        hazardousMaterial.getHazardousMaterial().getHazardousMaterialCode(),
                        hazardousMaterial.getHazardousMaterial().getHazardousMaterialName(),
                        hazardousMaterial.getHazardousMaterial().getHazardLevel(),
                        hazardousMaterial.getHazardousMaterial().getDescription()
                )).collect(Collectors.toList());

        dto.setProduct(productMaterialList);
        dto.setHazardousMaterial(hazardousMaterialList);

        return dto;
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
        dto.setHazardousMaterialQuantity((long)updateMaterial.getMaterialHazardous().size());

        return dto;
    }
}
