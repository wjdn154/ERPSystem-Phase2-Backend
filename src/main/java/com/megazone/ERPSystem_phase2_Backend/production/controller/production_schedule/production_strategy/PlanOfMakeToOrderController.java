package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.production_strategy.PlanOfMakeToOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production/MtoPlan/")
@RequiredArgsConstructor
public class PlanOfMakeToOrderController {

    private final PlanOfMakeToOrderService planOfMakeToOrderService;

    @PostMapping("/new")
    public void newPlanOfMakeToOrder(@RequestBody PlanOfMakeToOrder planOfMakeToOrder) {}
}
