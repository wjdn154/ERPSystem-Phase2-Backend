package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.sale;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Quotation;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryCustom {
}
