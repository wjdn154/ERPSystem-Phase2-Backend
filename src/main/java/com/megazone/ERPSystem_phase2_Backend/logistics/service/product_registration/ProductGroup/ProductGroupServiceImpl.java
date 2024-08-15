package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService{

    private final ProductGroupRepository productGroupRepository;

    /**
     * 모든 품목그룹 조회
     *
     * @return
     */
    public List<ProductGroup> searchAllProductGroup() {
        return productGroupRepository.findAll();
    }

    /**
     * id로 폼목 그룹 조회
     * @param id
     * @return
     */
    @Override
    public ProductGroup searchByIdProductGroup(Long id) {
        return productGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id를 찾을 수가 없습니다. " + id));
    }

    /**
     * 새로운 품목 그룹 저장
     * @param
     * @return
     */
    @Override
    public ProductGroup saveProductGroup(ProductGroupDto dto) {
        ProductGroup productGroup = dto.toEntity();
        if (productGroupRepository.existsByCode(productGroup.getCode())) {
            throw new IllegalArgumentException("중복된 code입니다.");
        }

        return productGroupRepository.save(productGroup);
    }

    /**
     * 폼목 그룹 수정
     * @param id
     * @param dto
     * @return
     */
    @Override
    public ProductGroup updateProductGroup(Long id, ProductGroupDto dto) {
        ProductGroup productGroup = dto.toEntity();

        ProductGroup target = productGroupRepository.findById(id).orElse(null);
        if (target == null){
            throw new RuntimeException("존재하지 않는 품목 그룹입니다.");
        }

        return productGroupRepository.save(productGroup);
    }
}
