package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.hierarchy_group;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.HierarchyGroupResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QHierarchyGroup.hierarchyGroup;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HierarchyGroupRepositoryImpl implements HierarchyGroupRepositoryCustom{

    private final JPAQueryFactory queryFactory;

}
