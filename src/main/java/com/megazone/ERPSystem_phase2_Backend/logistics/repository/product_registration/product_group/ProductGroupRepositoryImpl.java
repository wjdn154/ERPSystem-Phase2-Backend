package com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProductGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductGroupRepositoryImpl implements ProductGroupRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductGroup> findByCodeAndName(String code, String name) {
        QProductGroup productGroup = QProductGroup.productGroup;

        BooleanBuilder builder = new BooleanBuilder();

        if (code != null && !code.isEmpty()) {
            builder.and(productGroup.code.containsIgnoreCase(code));
        }
        if (name != null && !name.isEmpty()) {
            builder.and(productGroup.name.containsIgnoreCase(name));
        }

        return queryFactory.selectFrom(productGroup)
                            .where(builder)
                            .fetch();
    }
}
