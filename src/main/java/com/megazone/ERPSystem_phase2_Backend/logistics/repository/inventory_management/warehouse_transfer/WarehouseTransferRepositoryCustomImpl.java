package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee.employee;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.QProduct.product;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.QWarehouseTransfer.warehouseTransfer;
import static com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.QWarehouseTransferProduct.warehouseTransferProduct;

@Repository
@RequiredArgsConstructor
public class WarehouseTransferRepositoryCustomImpl implements WarehouseTransferRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WarehouseTransferResponseListDTO> findTransfers(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.fields(WarehouseTransferResponseListDTO.class,
                        warehouseTransfer.id,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y/%m/%d')", warehouseTransfer.transferDate)
                                .concat(" - ")
                                .concat(warehouseTransfer.transferNumber.stringValue()).as("transferNumber"),  // 포맷팅된 이동 번호
                        warehouseTransfer.sendingWarehouse.name.as("sendingWarehouseName"),
                        warehouseTransfer.receivingWarehouse.name.as("receivingWarehouseName"),
                        Expressions.stringTemplate("MIN({0})", warehouseTransferProduct.product.name).as("productName"),  // 첫 번째 품목 이름
                        Expressions.stringTemplate("concat('외 ', {0}, '건')", warehouseTransferProduct.count().subtract(1)).as("additionalProducts"),  // 추가 품목 수
                        warehouseTransferProduct.quantity.sum().as("totalQuantity"),  // 총 수량
                        warehouseTransfer.status
                ))
                .from(warehouseTransfer)
                .join(warehouseTransfer.warehouseTransferProducts, warehouseTransferProduct)
                .join(warehouseTransferProduct.product, product)
                .where(warehouseTransfer.transferDate.between(startDate, endDate))
                .groupBy(warehouseTransfer.id)
                .fetch();
    }


    @Override
    public Optional<WarehouseTransferResponseDTO> findTransferDetailById(Long transferId) {
        WarehouseTransferResponseDTO transferDetail = queryFactory
                .select(Projections.fields(WarehouseTransferResponseDTO.class,
                        warehouseTransfer.id,
                        warehouseTransfer.transferDate,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y/%m/%d')", warehouseTransfer.transferDate)
                                .concat(" - ")
                                .concat(warehouseTransfer.transferNumber.stringValue()).as("transferNumber"),
                        warehouseTransfer.sendingWarehouse.name.as("sendingWarehouseName"),
                        warehouseTransfer.receivingWarehouse.name.as("receivingWarehouseName"),
                        Expressions.stringTemplate("CONCAT({0}, ' ', {1})", employee.firstName, employee.lastName).as("employeeName"),
                        warehouseTransfer.comment.as("comment"),
                        warehouseTransfer.status
                ))
                .from(warehouseTransfer)
                .join(warehouseTransfer.sendingWarehouse)
                .join(warehouseTransfer.receivingWarehouse)
                .join(warehouseTransfer.employee, employee)
                .where(warehouseTransfer.id.eq(transferId))
                .fetchOne();

        if (transferDetail == null) {
            return Optional.empty();
        }

        // 이동 품목 리스트 조회
        List<WarehouseTransferProductResponseDTO> products = queryFactory
                .select(Projections.fields(WarehouseTransferProductResponseDTO.class,
                        warehouseTransferProduct.product.id,
                        warehouseTransferProduct.product.code.as("productCode"),
                        warehouseTransferProduct.product.name.as("productName"),
                        warehouseTransferProduct.quantity.as("quantity"),
                        warehouseTransferProduct.comment.as("comment")
                ))
                .from(warehouseTransferProduct)
                .join(warehouseTransferProduct.product, product)
                .where(warehouseTransferProduct.warehouseTransfer.id.eq(transferId))
                .fetch();

        transferDetail.setProducts(products);
        return Optional.of(transferDetail);
    }

    @Override
    public Long findMaxTransferNumberByDate(LocalDate transferDate) {
        Long maxTransferNumber = queryFactory
                .select(warehouseTransfer.transferNumber.max())
                .from(warehouseTransfer)
                .where(warehouseTransfer.transferDate.eq(transferDate))
                .fetchOne();

        return maxTransferNumber != null ? maxTransferNumber : 0L;
    }

    @Override
    public Long getNextTransferNumber(LocalDate transferDate) {
        Long count = queryFactory
                .select(warehouseTransfer.count())
                .from(warehouseTransfer)
                .where(warehouseTransfer.transferDate.eq(transferDate))
                .fetchOne();
        return (count != null ? count : 0) + 1;
    }

}