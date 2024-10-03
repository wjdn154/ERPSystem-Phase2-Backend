package com.megazone.ERPSystem_phase2_Backend.production.repository.work_performance.goods_receipt.InboundRegistration;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.InboundRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRegistrationRepository extends JpaRepository<InboundRegistration, Long> ,InboundRegistrationRepositoryCustom{
}
