package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;

import java.util.List;
import java.util.Optional;


public interface ProductGroupService {

    List<ProductGroupDto> findAllProductGroups();

    ProductGroupDto getProductGroupById(Long id);

    Optional<ProductGroupDto> saveProductGroup(ProductGroupDto productGroupDto);

    Optional<ProductGroupDto> updateProduct(Long id, ProductGroupDto productGroupDto);

    String deleteProductGroup(Long id);
}
