package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter.workcenter;

@Repository
@RequiredArgsConstructor
public class WorkcenterRepositoryImpl implements WorkcenterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Workcenter> findByName(String name) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.name.eq(name))
                .fetch();
    }

    @Override
    public List<Workcenter> findByCode(String code) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.code.eq(code))
                .fetch();
    }

    @Override
    public List<Workcenter> findByActive(boolean active) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.active.eq(true))
                .fetch();
    }
}
