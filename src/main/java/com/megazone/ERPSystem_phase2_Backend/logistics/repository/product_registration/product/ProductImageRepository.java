package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, ProductImageRepositoryCustom {
}
