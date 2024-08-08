package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService{
    @Override
    public ProductGroup saveProductGroup(ProductGroup group) {
        return null;
    }

    @Override
    public ProductGroup updateProductGroup(ProductGroup group) {
        return null;
    }

    @Override
    public void deleteProductGroup(Long productGroupId) {

    }
}
