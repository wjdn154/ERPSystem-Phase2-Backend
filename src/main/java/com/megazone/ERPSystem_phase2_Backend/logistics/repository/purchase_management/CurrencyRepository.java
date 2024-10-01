package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
