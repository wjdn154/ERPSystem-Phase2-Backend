package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;

import java.util.List;

public interface ReceivingOrderRepositoryCustom {
    List<ReceivingOrder> findBySearch(SearchDTO dto);
}
