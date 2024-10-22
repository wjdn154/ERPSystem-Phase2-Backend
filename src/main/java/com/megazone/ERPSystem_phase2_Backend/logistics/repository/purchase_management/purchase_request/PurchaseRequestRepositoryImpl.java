package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_request;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.QPurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.QPurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct.product;

@Repository
@RequiredArgsConstructor
public class PurchaseRequestRepositoryImpl implements PurchaseRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PurchaseRequestResponseDto> searchPurchaseRequests(PurchaseRequestSearchDTO dto) {

        QPurchaseRequest purchaseRequest = QPurchaseRequest.purchaseRequest;
        QPurchaseRequestDetail purchaseRequestDetail = QPurchaseRequestDetail.purchaseRequestDetail;
        QClient client = QClient.client;

        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            builder.and(purchaseRequest.date.between(dto.getStartDate(), dto.getEndDate()));
        }

        if (dto.getClientCode() != null && !dto.getClientCode().isEmpty()) {
            builder.and(client.code.eq(dto.getClientCode()));
        }

        if (dto.getState() != null && !dto.getState().isEmpty()) {
            builder.and(purchaseRequest.status.eq(State.valueOf(dto.getState())));
        }

        return queryFactory
                .select(Projections.constructor(PurchaseRequestResponseDto.class,
                        purchaseRequest.id,
                        purchaseRequestDetail.product.client.printClientName,  // 첫 번째 품목의 거래처 이름
                        // 첫 번째 품목 이름과 외 N건 처리
                        Expressions.stringTemplate("case when count(purchaseRequestDetail) > 1 then concat({0}, ' 외 ', {1}, '건') else {0} end",
                                purchaseRequest.purchaseRequestDetails.get(0).product.name, purchaseRequestDetail.count().subtract(1)),
                        purchaseRequest.date,
                        purchaseRequest.deliveryDate,
                        purchaseRequestDetail.quantity.sum(),  // 총 수량 합계
                        purchaseRequestDetail.supplyPrice.sum(),  // 총 공급가액 합계
                        purchaseRequest.status.stringValue()  // 진행 상태
                ))
                .from(purchaseRequest)
                .join(purchaseRequest.purchaseRequestDetails, purchaseRequestDetail)  // 발주 요청과 발주 요청 상세 조인
                .join(purchaseRequestDetail.product, product)  // 발주 요청 상세와 품목 조인
                .join(product.client, client)  // 품목을 통해 거래처와 조인
                .where(builder)  // 조건 추가 (필요 시 적용)
                .groupBy(purchaseRequest.id, purchaseRequestDetail.product.client.printClientName, purchaseRequest.date, purchaseRequest.deliveryDate, purchaseRequest.status)  // Group By 발주 요청 ID로 묶음
                .fetch();
    }
}
