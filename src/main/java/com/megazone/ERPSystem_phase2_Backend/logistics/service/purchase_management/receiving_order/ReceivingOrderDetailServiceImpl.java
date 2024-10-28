package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderDetailResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order.ReceivingOrderDetailRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order.ReceivingOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceivingOrderDetailServiceImpl implements ReceivingOrderDetailService {

    private final ReceivingOrderRepository receivingOrderRepository;
    private final ReceivingOrderDetailRepository receivingOrderDetailRepository;

    @Override
    public List<ReceivingOrderDetailResponseDTO> getReceivingOrderDetailsWithWaitingQuantityByDateRange(LocalDate startDate, LocalDate endDate) {
        return receivingOrderDetailRepository.findWaitingForReceiptDetailsByDateRange(startDate, endDate);
    }
}
