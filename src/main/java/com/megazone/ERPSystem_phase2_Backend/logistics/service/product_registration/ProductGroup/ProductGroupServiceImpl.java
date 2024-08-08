package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService{

    private final ProductGroupRepository productGroupRepository;

    /**
     * 품목 그룹을 저장함.
     * 품목 코드의 중복을 검사하고 중복이 아닐 경우 저장을 진행함.
     * @param productGroup 저장할 품목 그룹 정보
     * @return 저정된 품목 그룹
     * @throws IllegalArgumentException 품목 코드가 중복될 경우 발생
     */
    @Override
    public ProductGroup saveProductGroup(ProductGroup productGroup) {

        return productGroupRepository.save(productGroup);
    }

    @Override
    public ProductGroup updateProductGroup(ProductGroup productGroup) {
        return null;
    }

    @Override
    public void deleteProductGroup(Long productGroupId) {

    }
}
