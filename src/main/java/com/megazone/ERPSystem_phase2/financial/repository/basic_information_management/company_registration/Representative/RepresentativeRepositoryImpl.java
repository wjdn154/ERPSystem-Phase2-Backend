package com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative;


import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.QRepresentative;
import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepresentativeRepositoryImpl implements RepresentativeRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    @Autowired
    public RepresentativeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Representative> findByName(String name) {
        QRepresentative representative = QRepresentative.representative;
        return queryFactory.selectFrom(representative)
                .where(representative.name.eq(name))
                .fetch();
    }
}