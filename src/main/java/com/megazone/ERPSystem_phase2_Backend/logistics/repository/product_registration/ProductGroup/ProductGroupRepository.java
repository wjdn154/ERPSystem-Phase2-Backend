package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}

