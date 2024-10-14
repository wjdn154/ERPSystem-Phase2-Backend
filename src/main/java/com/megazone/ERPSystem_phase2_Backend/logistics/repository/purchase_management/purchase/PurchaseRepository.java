package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseRepositoryCustom {
}
