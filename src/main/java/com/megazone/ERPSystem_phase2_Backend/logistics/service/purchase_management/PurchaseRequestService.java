package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import org.springframework.http.ResponseEntity;

public interface PurchaseRequestService {

    ResponseEntity<Object> findAllPurchaseRequests();
}
