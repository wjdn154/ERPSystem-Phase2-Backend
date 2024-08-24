package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}
