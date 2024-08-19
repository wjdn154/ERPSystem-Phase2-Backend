package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EquipmentDataRepositoryImpl implements EquipmentDataRepositoryCustom{

    private final JPAQueryFactory queryFactory;


}
