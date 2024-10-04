package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.production_strategy;

import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.production_strategy.PlanOfMakeToStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production/MtsPlan/")
@RequiredArgsConstructor
public class PlanOfMakeToStockController {
    private final PlanOfMakeToStockService planOfMakeToStockService;

    @PostMapping("/new")
    public void newPlanOfMakeToStock() {}
}
