package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;

import java.util.List;
import java.util.Optional;


public interface ProductGroupService {

    List<ProductGroupDto> findAllProductGroups(Long companyId, String searchTerm);

    Optional<ProductGroupDto> saveProductGroup(Long companyId, ProductGroupDto productGroupDto);

    Optional<ProductGroupDto> updateProduct(Long companyId, Long id, ProductGroupDto productGroupDto);

    String deleteProductGroup(Long companyId, Long id);

    String deactivateProductGroup(Long companyId, Long id);

    String reactivateProductGroup(Long companyId, Long id);
}
