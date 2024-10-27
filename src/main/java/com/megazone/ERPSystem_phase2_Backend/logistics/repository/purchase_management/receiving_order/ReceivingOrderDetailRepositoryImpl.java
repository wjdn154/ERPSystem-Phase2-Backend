package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.State;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderDetailResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.QReceivingOrder.receivingOrder;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.QReceivingOrderDetail.receivingOrderDetail;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.QReceivingSchedule.receivingSchedule;

@Repository
@RequiredArgsConstructor
public class ReceivingOrderDetailRepositoryImpl implements ReceivingOrderDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<ReceivingOrderDetailResponseDTO> findWaitingForReceiptDetailsByDateRange(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(
                        ReceivingOrderDetailResponseDTO.class,
                        receivingOrderDetail.id,
                        receivingOrderDetail.receivingOrder.id,
                        receivingOrderDetail.product.id,
                        receivingOrderDetail.product.name,
                        receivingOrderDetail.receivingOrder.receivingWarehouse.id,
                        receivingOrderDetail.receivingOrder.receivingWarehouse.name,
                        receivingOrderDetail.receivingOrder.date.stringValue(),
                        receivingOrderDetail.receivingOrder.deliveryDate.stringValue(),
                        receivingOrderDetail.quantity,
                        receivingSchedule.pendingQuantity.sum().coalesce(0).as("totalWaitingQuantity"),
                        receivingOrderDetail.unreceivedQuantity,
                        receivingOrderDetail.remarks
                ))
                .from(receivingOrderDetail)
                .join(receivingOrderDetail.receivingOrder, receivingOrder)
                .leftJoin(receivingOrderDetail.receivingSchedules, receivingSchedule)
                .where(receivingOrder.status.eq(State.WAITING_FOR_RECEIPT)
                                .and(receivingOrderDetail.unreceivedQuantity.ne(0))
                                .and(receivingOrder.date.between(startDate, endDate))
                )
                .groupBy(receivingOrderDetail.id)
                .fetch();
    }
}
