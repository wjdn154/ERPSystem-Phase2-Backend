package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD:src/main/java/com/megazone/ERPSystem_phase2_Backend/logistics/repository/product_registration/product/ProductRepository.java
=======
import java.util.List;
import java.util.Optional;

>>>>>>> origin/develop:src/main/java/com/megazone/ERPSystem_phase2_Backend/logistics/repository/product_registration/Product/ProductRepository.java
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    Optional<Product> findByCode(String code);

    Optional<Product> findByName(String name);

    List<Product> findByCodeContainingOrNameContaining(String code, String name);

}
