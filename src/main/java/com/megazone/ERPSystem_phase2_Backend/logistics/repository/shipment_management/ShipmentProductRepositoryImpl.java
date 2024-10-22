package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.QShipmentProduct.shipmentProduct;

@Repository
@RequiredArgsConstructor
public class ShipmentProductRepositoryImpl implements ShipmentProductRepositoryCustom {

    private final JPAQueryFactory equeryFactory;

    /**
     * 날짜 범위로 출하 목록 조회 및 총합 조회
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<ShipmentProduct> findShipmentItemsByDateRange(LocalDate startDate, LocalDate endDate) {
        return equeryFactory
                .select(shipmentProduct)
                .from(shipmentProduct)
                .where(shipmentProduct.shipment.shipmentDate.between(startDate, endDate))
                .fetch();
    }
}
