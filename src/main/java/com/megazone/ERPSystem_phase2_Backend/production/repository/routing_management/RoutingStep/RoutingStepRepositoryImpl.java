package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.RoutingStep;

import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoutingStepRepositoryImpl implements RoutingStepRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
