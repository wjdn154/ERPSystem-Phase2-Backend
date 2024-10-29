package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipping_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.ShippingProcessing;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.enums.ShippingStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.QShippingProcessing.shippingProcessing;

@Repository
@RequiredArgsConstructor
public class ShippingProcessingRepositoryImpl implements ShippingProcessingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long getMaxShippingNumberByDate(LocalDate shippingDate) {
        return queryFactory
                .select(shippingProcessing.shippingNumber.max())
                .from(shippingProcessing)
                .where(shippingProcessing.shippingDate.eq(shippingDate))
                .fetchOne();
    }

    @Override
    public List<ShippingProcessing> findShippingProcessingByDateRangeAndStatus(LocalDate startDate, LocalDate endDate) {
        return queryFactory.selectFrom(shippingProcessing)
                .where(shippingProcessing.shippingDate.between(startDate, endDate)
                        .and(shippingProcessing.shippingStatus.eq(ShippingStatus.WAITING_FOR_SHIPMENT)))
                .fetch();
    }
}
