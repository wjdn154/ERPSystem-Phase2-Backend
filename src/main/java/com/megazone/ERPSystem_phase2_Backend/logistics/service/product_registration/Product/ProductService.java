package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.Product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;

public interface ProductService {
    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);
}
