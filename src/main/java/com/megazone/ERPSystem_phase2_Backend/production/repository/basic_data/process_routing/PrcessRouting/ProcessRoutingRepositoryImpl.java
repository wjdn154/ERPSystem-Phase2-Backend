package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.QProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.QRoutingStep;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessRoutingRepositoryImpl implements ProcessRoutingRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QProcessRouting processRouting = QProcessRouting.processRouting;

    @Override
    public List<ProcessRouting> findRoutingsByProcessDetails(Long processId) {
        QRoutingStep routingStep = QRoutingStep.routingStep;

        return queryFactory.selectFrom(processRouting)
                .join(processRouting.routingSteps, routingStep)
                .where(routingStep.processDetails.id.eq(processId))
                .fetch();

    }

    @Override
    public List<ProcessRouting> findRoutingsByProduct(Long productId) {
        QProduct product = QProduct.product;

        return queryFactory.selectFrom(processRouting)
                .join(processRouting.products, product)
                .where(product.id.eq(productId))
                .fetch();
    }


}
