package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.HierarchyGroup;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HierarchyGroupRepositoryImpl implements HierarchyGroupRepositoryCustom{

    private final JPAQueryFactory queryFactory;

}
