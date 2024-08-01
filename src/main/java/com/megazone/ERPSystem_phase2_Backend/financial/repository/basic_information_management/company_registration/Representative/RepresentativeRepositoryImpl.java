package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company_registration.Representative;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.QRepresentative.*;

@Repository
@RequiredArgsConstructor
public class RepresentativeRepositoryImpl implements RepresentativeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 이름으로 대표자 검색 메서드
     *
     * @param name 대표자의 이름
     * @return 해당 이름을 가진 Representative 리스트
     */
    @Override
    public List<Representative> findByName(String name) {
        return queryFactory
                .selectFrom(representative)
                .where(representative.name.eq(name)) // 이름이 일치하는 조건
                .fetch(); // 결과를 리스트로 반환
    }
}