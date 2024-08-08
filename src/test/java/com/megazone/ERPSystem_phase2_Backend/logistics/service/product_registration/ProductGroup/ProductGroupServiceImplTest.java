package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductGroupServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired private ProductGroupRepository productGroupRepository;

    @Autowired private ProductGroupService productGroupService;

    @BeforeEach
    void setUp() {
        productGroupRepository.deleteAll();

        ProductGroup productGroup = new ProductGroup();
        productGroup.setCode("A01");
        productGroup.setName("컴퓨터");
        productGroupRepository.save(productGroup);
        productGroupService.saveProductGroup(productGroup);
    }

    @Test
    void saveProductGroup() {
        // given

        // when
        ProductGroup productGroup = productGroupRepository.findByCode("A01").orElseThrow();

        // then
        assertEquals("A01", productGroup.getCode(), "저장된 품목 그룹의 코드는 제공된 값과 일치해야 함");
    }

}