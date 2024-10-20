package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentCountDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentSummaryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.WorkerAssignment.WorkerAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/production/workerAssignment")
@RequiredArgsConstructor
public class WorkerAssignmentController {

    private final WorkerAssignmentService workerAssignmentService;

    // 전체 작업장별 배정된 인원수 조회
    @PostMapping("/workcenters/count")
    public List<WorkerAssignmentCountDTO> getAllWorkcentersWorkerCount() {
        return workerAssignmentService.getAllWorkcentersWorkerCount();
    }

    // 특정 작업장 배정된 작업자 명단 조회
    @PostMapping("/workcenter/{workcenterCode}")
    public List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(@PathVariable("workcenterCode") String workcenterCode) {
        return workerAssignmentService.getWorkersByWorkcenterCode(workcenterCode);
    }

    // 특정 날짜에 작업자 배정 상태 확인
    @PostMapping("/check")
    public boolean isWorkerAlreadyAssigned(@RequestParam(value = "workerId") Long workerId, @RequestParam(value = "date") LocalDate date) {
        return workerAssignmentService.isWorkerAlreadyAssigned(workerId, date);
    }

    //  ==작업지시에서 작업장에 작업자를 배정하는 동시에 자동생성됨 수정삭제 없음==

    // 일별 모든 작업장의 작업자 배정 이력 조회
    @PostMapping("/daily")
    public WorkerAssignmentSummaryDTO getDailyWorkerAssignments(
            @RequestParam(value = "date") LocalDate date,
            @RequestParam(value = "includeShiftType", required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(value = "shiftTypeId", required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByDate(date, includeShiftType, shiftTypeId);
    }

    // 월별 모든 작업장의 작업자 배정 이력 조회
    @PostMapping("/monthly")
    public List<WorkerAssignmentDTO> getMonthlyWorkerAssignments(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return workerAssignmentService.getWorkerAssignmentsByDateRange(startOfMonth, endOfMonth);
    }


//    // 오늘의 모든 작업장의 작업자 배정 조회. 단, 공장별로 구분해서 교대유형 구분없이 전체 조회하되, 교대유형 구분 옵션 또한 필요
//    @PostMapping("/today")
//    public List<WorkerAssignmentSummaryDTO> getTodayWorkerAssignments(
//            @RequestParam(required = false, defaultValue = "false") boolean includeShiftType,
//            @RequestParam(required = false) Long shiftTypeId) {
//        LocalDate today = LocalDate.now();
//        return workerAssignmentService.getTodayWorkerAssignments(today, includeShiftType, shiftTypeId);
//    }

    // 작업장별 오늘의 배정인원 상세명단 조회 + 작업자수 조회 (프론트에서 전체조회 시 작업자수 반환, 상세조회 시 상세명단 반환)
    // 작업장별 오늘의 배정인원 상세명단과 인원수 조회
    @PostMapping("/today/summary")
    public WorkerAssignmentSummaryDTO getTodayWorkerAssignmentsSummary(
            @RequestParam(value = "includeShiftType") boolean includeShiftType,
            @RequestParam(value = "shiftType") Long shiftTypeId) {
        LocalDate today = LocalDate.now(ZoneId.of("UTC")); // 서버와 DB의 타임존 일치
        return workerAssignmentService.getTodayWorkerAssignmentsSummary(today, includeShiftType, shiftTypeId);
    }


    // 작업지시별 작업자명단 조회 + 작업자수 조회 (프론트에서 전체조회 시 작업자수 반환, 상세조회 시 상세명단 반환)
    @PostMapping("/productionOrder/{productionOrderId}/summary")
    public WorkerAssignmentSummaryDTO getWorkerAssignmentsByProductionOrder(
            @PathVariable("productionOrderId") Long productionOrderId,
            @RequestParam(value = "includeShiftType", required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(value = "shiftTypeId", required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByProductionOrder(productionOrderId, includeShiftType, shiftTypeId);
    }

    // 작업자별 배치이력 조회
    @PostMapping("/worker/{workerId}/assignments")
    public List<WorkerAssignmentDTO> getWorkerAssignmentsByWorker(
            @PathVariable("workerId") Long workerId,
            @RequestParam(value = "includeShiftType", required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(value = "shiftTypeId", required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByWorker(workerId, includeShiftType, shiftTypeId);
    }

    // 오늘의 모든 작업장의 작업자 배정 명단 엑셀파일 다운로드 (보류)
    @PostMapping("/today/excel")
    public void downloadTodayWorkerAssignmentsExcel() {
        // 엑셀 파일 다운로드 로직은 보류
    }
}
