package com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_history;

import java.time.LocalDate;

public interface InventoryHistoryRepositoryCustom {
    Long findMaxWorkNumberByDate(LocalDate workDate);

    Long findMaxSlipNumberByDate(LocalDate slipDate);
}
