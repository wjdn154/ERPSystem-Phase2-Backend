package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order_details;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.enums.ShippingStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient.client;
import static com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee.employee;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct.product;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.QShippingOrder.shippingOrder;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.QShippingOrderDetail.shippingOrderDetail;

@Repository
@RequiredArgsConstructor
public class ShippingOrderDetailRepositoryImpl implements ShippingOrderDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ShippingOrderDetail> findDetailsByOrderDateRange(LocalDate startDate, LocalDate endDate) {
        return queryFactory.selectFrom(shippingOrderDetail)
                .join(shippingOrderDetail.shippingOrder, shippingOrder).fetchJoin()
                .join(shippingOrder.client, client).fetchJoin()
                .join(shippingOrder.manager, employee).fetchJoin()
                .join(shippingOrderDetail.product, product).fetchJoin()
                .where(shippingOrder.date.between(startDate, endDate).and(shippingOrderDetail.shippingStatus.eq(ShippingStatus.ORDER_FOR_SHIPMENT)))
                .fetch();
    }
}
