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

        //QueryDSL에서 조건을 동적으로 추가할 수 있도록 도와주는 클래스 이 객체에 조건을 추가하면서 쿼리를 구성
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
