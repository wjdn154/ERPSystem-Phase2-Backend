package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order_details;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrderDetail.ShippingOrderDetailResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order_details.ShippingOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippingOrderDetailServiceImpl implements ShippingOrderDetailService {

    private final ShippingOrderDetailRepository shippingOrderDetailRepository;

    @Override
    public List<ShippingOrderDetailResponseDTO> getShippingOrderDetailsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ShippingOrderDetail> details = shippingOrderDetailRepository.findDetailsByOrderDateRange(startDate, endDate);

        // 엔티티를 DTO로 변환
        return details.stream()
                .map(ShippingOrderDetailResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
