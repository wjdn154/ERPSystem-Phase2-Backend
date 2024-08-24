package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;

import java.util.List;

public interface ProductGroupRepositoryCustom {
    List<ProductGroup> findByCodeAndName(String code, String name);
}