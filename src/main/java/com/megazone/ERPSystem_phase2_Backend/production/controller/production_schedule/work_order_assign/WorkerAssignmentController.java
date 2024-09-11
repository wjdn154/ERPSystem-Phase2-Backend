package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.work_order_assign;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentSummaryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.worker_assignment.WorkerAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/production/workerAssignment")
@RequiredArgsConstructor
public class WorkerAssignmentController {

    private final WorkerAssignmentService workerAssignmentService;
    private final CompanyRepository companyRepository;
    private final UsersRepository usersRepository;

    // JWT 인증에서 사용자 정보를 가져오는 메서드
    private Long getCurrentCompanyId() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getCompany().getId();
    }

    // 전체 작업장별 배정된 인원수 조회
    @PostMapping("/workcenters/count")
    public List<WorkerAssignmentDTO> getAllWorkcentersWorkerCount() {
        return workerAssignmentService.getAllWorkcentersWorkerCount();
    }

    // 특정 작업장 배정된 작업자 명단 조회
    @PostMapping("/workcenter/{workcenterCode}")
    public List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(@PathVariable String workcenterCode) {
        return workerAssignmentService.getWorkersByWorkcenterCode(workcenterCode);
    }

    // 특정 날짜에 작업자 배정 상태 확인
    @PostMapping("/check")
    public boolean isWorkerAlreadyAssigned(@RequestParam Long workerId, @RequestParam LocalDate date) {
        return workerAssignmentService.isWorkerAlreadyAssigned(workerId, date);
    }

    //  ==작업지시에서 작업장에 작업자를 배정하는 동시에 자동생성됨 수정삭제 없음==

    // 일별 모든 작업장의 작업자 배정 이력 조회
    @PostMapping("/daily")
    public WorkerAssignmentSummaryDTO getDailyWorkerAssignments(
            @RequestParam LocalDate date,
            @RequestParam(required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByDate(date, includeShiftType, shiftTypeId);
    }

    // 월별 모든 작업장의 작업자 배정 이력 조회
    @PostMapping("/monthly")
    public List<WorkerAssignmentDTO> getMonthlyWorkerAssignments(@RequestParam int year, @RequestParam int month) {
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
    @PostMapping("/today/summary/")
    public WorkerAssignmentSummaryDTO getTodayWorkerAssignmentsSummary(
            @RequestParam("includeShiftType") boolean includeShiftType,
            @RequestParam("shiftType") Long shiftTypeId) {
        LocalDate today = LocalDate.now();
        return workerAssignmentService.getTodayWorkerAssignmentsSummary(today, includeShiftType, shiftTypeId);
    }


    // 작업지시별 작업자명단 조회 + 작업자수 조회 (프론트에서 전체조회 시 작업자수 반환, 상세조회 시 상세명단 반환)
    @PostMapping("/workOrder/{workOrderId}/summary")
    public WorkerAssignmentSummaryDTO getWorkerAssignmentsByWorkOrder(
            @PathVariable Long workOrderId,
            @RequestParam(required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByWorkOrder(workOrderId, includeShiftType, shiftTypeId);
    }

    // 작업자별 배치이력 조회
    @PostMapping("/worker/{workerId}/assignments")
    public List<WorkerAssignmentDTO> getWorkerAssignmentsByWorker(
            @PathVariable Long workerId,
            @RequestParam(required = false, defaultValue = "false") boolean includeShiftType,
            @RequestParam(required = false) Long shiftTypeId) {
        return workerAssignmentService.getWorkerAssignmentsByWorker(workerId, includeShiftType, shiftTypeId);
    }

    // 오늘의 모든 작업장의 작업자 배정 명단 엑셀파일 다운로드 (보류)
    @PostMapping("/today/excel")
    public void downloadTodayWorkerAssignmentsExcel() {
        // 엑셀 파일 다운로드 로직은 보류
    }
}
