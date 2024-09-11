package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule;

import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.worker_assignment.WorkerAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production/workerAssignment")
@RequiredArgsConstructor
public class WorkerAssignmentController {

    private final WorkerAssignmentService workerAssignmentService;

    // 작업지시에서 작업장에 작업자를 배정하는 동시에 자동생성됨 수정삭제 없음
    // 일.월별 모든 작업장의 작업자 배정이력 조회

    // 오늘의 모든 작업장의 작업자 배정 조회. 단, 공장별로 구분해서 교대유형 구분없이 전체 조회하되, 교대유형 구분 옵션 또한 필요

    // 작업장별 오늘의 배정인원 상세명단 조회 + 작업자수 조회 (프론트에서 전체조회 시 작업자수 반환, 상세조회 시 상세명단 반환)

    // 작업지시별 작업자명단 조회 + 작업자수 조회 (프론트에서 전체조회 시 작업자수 반환, 상세조회 시 상세명단 반환)

    // 작업자별 배치이력 조회
    // (보류) 오늘의 모든 작업장의 작업자 배정 명단 엑셀파일 다운로드

}
