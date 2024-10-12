package com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient.client;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.QShipment.shipment;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.QShipmentProduct.shipmentProduct;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse.warehouse;


@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findMaxShipmentNumberByDate(LocalDate shipmentDate) {
        Long maxShipmentNumber = queryFactory
                .select(shipment.shipmentNumber.max())
                .from(shipment)
                .where(shipment.shipmentDate.eq(shipmentDate))
                .fetchOne();

        return maxShipmentNumber != null ? maxShipmentNumber : 0L;
    }

    @Override
    public List<Shipment> findShipmentListByDateRange(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(shipment)
                .from(shipment)
                .leftJoin(shipment.warehouse, warehouse).fetchJoin()
                .leftJoin(shipment.client, client).fetchJoin()
                .leftJoin(shipment.products, shipmentProduct).fetchJoin()
                .where(shipment.shipmentDate.between(startDate, endDate))
                .distinct()
                .fetch();
    }

    @Override
    public Long findNextShipmentNumber(LocalDate shipmentDate) {
        Long maxNumber = queryFactory.select(shipment.shipmentNumber.max())
                .from(shipment)
                .where(shipment.shipmentDate.eq(shipmentDate))
                .fetchOne();
        return (maxNumber != null) ? maxNumber + 1 : 1;
    }

}
