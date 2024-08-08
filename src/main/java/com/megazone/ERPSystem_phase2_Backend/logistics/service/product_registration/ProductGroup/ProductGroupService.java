package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;

public interface ProductGroupService {

    ProductGroup saveProductGroup(ProductGroup productGroup);

    ProductGroup updateProductGroup(ProductGroup productGroup);

    void deleteProductGroup(Long productGroupId);
}
