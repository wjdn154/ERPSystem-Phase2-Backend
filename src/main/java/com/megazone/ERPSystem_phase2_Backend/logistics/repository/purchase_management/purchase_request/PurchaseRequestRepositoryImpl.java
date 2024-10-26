package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_request;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.QPurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseRequestRepositoryImpl implements PurchaseRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseRequest> findBySearch(SearchDTO dto) {
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;

        return queryFactory
                .selectFrom(purchaseRequest)
                .where(
                        buildSearchCondition(dto)  // 검색 조건 빌드
                )
                .fetch();
    }

    // 검색 조건을 동적으로 생성
    private BooleanBuilder buildSearchCondition(SearchDTO dto) {
        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        BooleanBuilder condition = new BooleanBuilder();  // BooleanBuilder로 동적 조건 생성

        // 발주 날짜 조건
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            condition.and(purchaseRequest.date.between(dto.getStartDate(), dto.getEndDate()));
        }

        // 거래처 코드 조건
        if (dto.getClientId() != null) {
            condition.and(purchaseRequest.purchaseRequestDetails.any().product.client.id.eq(dto.getClientId()));  // 거래처 코드 조건 추가
        }

        // 상태 조건 (String을 Enum으로 변환)
        if (dto.getState() != null && !dto.getState().isEmpty()) {

            // DTO에서 상태를 가져와 Enum으로 변환
            State enumState = State.valueOf(dto.getState().toUpperCase());

            // QueryDSL Enum 값 비교
            condition.and(purchaseRequest.status.eq(enumState));
        }

        return condition;
    }
}
