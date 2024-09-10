package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsByCode(String code);

    List<Product> findAllByCompanyId(Long companyId);

    boolean existsByCodeAndIdNot(String code, Long id);

    Optional<Product> findByCode(String code);

    Optional<Product> findByName(String name);

    List<Product> findByCodeContainingOrNameContaining(String code, String name);

    Optional<Product> findByCompanyIdAndId(Long companyId, Long id);
}
