package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    boolean existsByCode(String code);

<<<<<<< HEAD:src/main/java/com/megazone/ERPSystem_phase2_Backend/logistics/repository/product_registration/product_group/ProductGroupRepository.java
    boolean existsByCodeAndIdNot(String code, Long id);
=======
    Optional<ProductGroup> findByName(String name);

>>>>>>> origin/develop:src/main/java/com/megazone/ERPSystem_phase2_Backend/logistics/repository/product_registration/ProductGroup/ProductGroupRepository.java
}

