package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule;

import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.shift_type.ShiftTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production/shiftType")
@RequiredArgsConstructor
public class ShiftTypeController {
    private final ShiftTypeService shiftTypeService;

    // post: 교대유형 전체조회

    // post: 교대유형 상세조회

    // post: 교대유형 새로 등록

    // post: 교대유형 수정 - 유형이름 같으면 예외

    // post: 교대유형 삭제 - 사용 중이면 삭제불가
}
