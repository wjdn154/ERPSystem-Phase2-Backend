package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.fetchClientListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient.client;


@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    private Long id; // id
    private String representativeName; // 대표자명
    private String printClientName; // 상호명
    private String roadAddress; // 도로명 주소
    private String detailedAddress; // 상세 주소
    private String phoneNumber; // 전화번호
    private String businessType; // 사업종류
    private LocalDate transactionStartDate; // 거래 시작일
    private LocalDate transactionEndDate; // 거래 종료일
    private String remarks; // 비고


    @Override
    public List<fetchClientListDTO> fetchClientList() {
        return queryFactory
                .select(Projections.fields(fetchClientListDTO.class,
                        client.id,
                        client.representativeName,
                        client.printClientName,
                        client.address.roadAddress,
                        client.address.detailedAddress,
                        client.contactInfo.phoneNumber,
                        client.businessInfo.businessType,
                        client.transactionStartDate,
                        client.transactionEndDate,
                        client.remarks
                )).from(QClient.client)
                .fetch();

    }
}