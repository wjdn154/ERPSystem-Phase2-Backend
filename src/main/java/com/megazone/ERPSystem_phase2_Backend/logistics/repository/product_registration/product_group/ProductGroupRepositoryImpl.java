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
    public List<ProductGroup> findProductGroupsBySearchTerm(String searchTerm) {
        QProductGroup productGroup = QProductGroup.productGroup;

        BooleanBuilder builder = new BooleanBuilder();

        // 검색어가 있는 경우에만 필터링
        if (searchTerm != null && !searchTerm.isEmpty()) {
            builder.and(productGroup.code.containsIgnoreCase(searchTerm)
                    .or(productGroup.name.containsIgnoreCase(searchTerm)));
        }

        // 쿼리 실행
        return queryFactory.selectFrom(productGroup)
                .where(builder)
                .fetch();
    }
}
