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