package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.PurchaseRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    @GetMapping
    public ResponseEntity<List<PurchaseRequestDTO>> getAllPurchaseRequests() {
        return ResponseEntity.ok(purchaseRequestService.getAllPurchaseRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequestDTO> getPurchaseRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseRequestService.getPurchaseRequestById(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseRequestDTO> createPurchaseRequest(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return ResponseEntity.ok(purchaseRequestService.createPurchaseRequest(purchaseRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseRequestDTO> updatePurchaseRequest(@PathVariable Long id, @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        return ResponseEntity.ok(purchaseRequestService.updatePurchaseRequest(id, purchaseRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseRequest(@PathVariable Long id) {
        purchaseRequestService.deletePurchaseRequest(id);
        return ResponseEntity.noContent().build();
    }
}