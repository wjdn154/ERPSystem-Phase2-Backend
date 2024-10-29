package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrderDetail.ShippingOrderDetailResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order_details.ShippingOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/shipping-order-details")
public class ShippingOrderDetailController {

    private final ShippingOrderDetailService shippingOrderDetailService;

    @PostMapping("/details")
    public ResponseEntity<List<ShippingOrderDetailResponseDTO>> getShippingOrderDetailsByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {

        List<ShippingOrderDetailResponseDTO> details = shippingOrderDetailService.getShippingOrderDetailsByDateRange(startDate, endDate);
        return ResponseEntity.ok(details);
    }

}
