package com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.VatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VatTypeRepository extends JpaRepository<VatType, Long> {
    @Query("SELECT v FROM VatType v JOIN FETCH v.accountSubject WHERE v.code = :code")
    VatType findByCode(@Param("code")String code);
}
