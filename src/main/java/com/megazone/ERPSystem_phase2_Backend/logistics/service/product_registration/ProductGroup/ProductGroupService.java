package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;

public interface ProductGroupService {

    ProductGroup saveProductGroup(ProductGroup group);

    ProductGroup updateProductGroup(ProductGroup group);

    void deleteProductGroup(Long productGroupId);
}
