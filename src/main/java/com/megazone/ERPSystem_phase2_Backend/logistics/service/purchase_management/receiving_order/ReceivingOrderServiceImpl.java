package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order;


import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order.ReceivingOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReceivingOrderServiceImpl implements ReceivingOrderService {

    private final ReceivingOrderRepository receivingOrderRepository;


    /**
     * 입고지시서 목록 조회
     * @return
     */
    @Override
    public List<ReceivingOrderResponseDto> findAllReceivingOrders() {

        List<ReceivingOrder> receivingOrders = receivingOrderRepository.findAll();

        if (receivingOrders.isEmpty()) {
            return new ArrayList<>();
        }

        return receivingOrders.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 입고지시서 목록 조회 관련 메서드 **/
    // Entity -> 입고지시서 목록 조회용 DTO 변환 메소드
    private ReceivingOrderResponseDto toListDto(ReceivingOrder receivingOrder) {
        return ReceivingOrderResponseDto.builder()
                .id(receivingOrder.getId())
                .clientName(receivingOrder.getClient().getPrintClientName())
                .managerName(receivingOrder.getManager().getLastName() + receivingOrder.getManager().getFirstName())
                .productName(getProductNameWithCount(receivingOrder))
                .totalQuantity(getTotalQuantity(receivingOrder))
                .date(receivingOrder.getDate())
                .deliveryDate(receivingOrder.getReceivingDate())
                .status(receivingOrder.getStatus().toString())
                .remarks(receivingOrder.getRemarks())
                .build();
    }

    // 총 수량 계산
    private Integer getTotalQuantity(ReceivingOrder receivingOrder) {
        return receivingOrder.getReceivingOrderDetails().stream()
                .mapToInt(ReceivingOrderDetail::getQuantity)
                .sum();
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(ReceivingOrder receivingOrder) {
        List<ReceivingOrderDetail> details = receivingOrder.getReceivingOrderDetails();

        if(details.isEmpty()) {
            return "품목 없음";
        }

        String firstProductName = details.get(0).getProduct().getName();

        if(details.size() > 1) {
            return firstProductName + " 외 " + (details.size() - 1) + "건";
        } else {
            return firstProductName;
        }
    }

    /**
     * 입고지시서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<Object> findReceivingOrderDetailById(Long id) {

        return Optional.empty();
    }

    
}
