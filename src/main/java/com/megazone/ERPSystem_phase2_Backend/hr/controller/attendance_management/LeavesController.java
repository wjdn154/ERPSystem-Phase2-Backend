package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Leaves.LeavesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/leaves")
public class LeavesController {
    private final LeavesService leavesService;
//    // 휴가 등록
//    @PostMapping("/createLeaves")
//    public String createLeaves(@RequestBody Leaves leaves) {
//
//    }
}
