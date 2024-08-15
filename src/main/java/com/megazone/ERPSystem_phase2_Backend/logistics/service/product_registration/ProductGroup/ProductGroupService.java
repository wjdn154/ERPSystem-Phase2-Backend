package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;

import java.util.List;


public interface ProductGroupService {

    List<ProductGroup> searchAllProductGroup();

    ProductGroup searchByIdProductGroup(Long id);

    ProductGroup saveProductGroup(ProductGroupDto dto);

    ProductGroup updateProductGroup(Long id, ProductGroupDto dto);

}
