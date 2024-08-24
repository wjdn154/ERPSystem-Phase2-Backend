package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QRoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.RoutingStep.RoutingStepRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductionRoutingRepositoryImpl implements ProductionRoutingRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QProductionRouting productionRouting = QProductionRouting.productionRouting;

    @Override
    public List<ProductionRouting> findRoutingsByProcessDetails(Long processId) {
        QRoutingStep routingStep = QRoutingStep.routingStep;

        return queryFactory.selectFrom(productionRouting)
                .join(productionRouting.routingSteps, routingStep)
                .where(routingStep.process.id.eq(processId))
                .fetch();

    }

    @Override
    public List<ProductionRouting> findRoutingsByProduct(Long productId) {
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(productionRouting)
                .join(productionRouting.products, product)
                .where(product.id.eq(productId))
                .fetch();
    }


}
