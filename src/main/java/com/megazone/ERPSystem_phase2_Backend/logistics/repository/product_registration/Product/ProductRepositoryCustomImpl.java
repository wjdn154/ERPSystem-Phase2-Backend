package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void nullifyProductGroupId(Long productGroupId) {
        QProduct product = QProduct.product;
        queryFactory.update(product)
                // 이 부분 이슈 적어야 함
                .set(product.productGroup, Expressions.nullExpression(ProductGroup.class))
                .where(product.productGroup.id.eq(productGroupId))
                .execute();
    }
}
