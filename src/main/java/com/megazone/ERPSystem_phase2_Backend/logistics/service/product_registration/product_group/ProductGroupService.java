package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;

import java.util.List;
import java.util.Optional;


public interface ProductGroupService {

    List<ProductGroupDto> findAllProductGroups(String searchTerm);

    Optional<ProductGroupDto> saveProductGroup(ProductGroupDto productGroupDto);

    Optional<ProductGroupDto> updateProduct(Long id, ProductGroupDto productGroupDto);

    String deleteProductGroup(Long id);

    String deactivateProductGroup(Long id);

    String reactivateProductGroup(Long id);
}
