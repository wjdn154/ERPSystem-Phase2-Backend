package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkerAssignmentRepositoryCustom {

    /**
     * 작업장별 작업자 수 조회
     * @return 작업자 배정 리스트
     */
    List<WorkerAssignment> findWorkerCountByWorkcenter();

    /**
     * 작업장 코드로 작업자 배정 검색
     * @param workcenterCode 작업장 코드
     * @return 작업자 배정(Optional로 감싸서 반환)
     */
    Optional<WorkerAssignment> findByWorkcenterCode(String workcenterCode);

    /**
     * 특정 작업자, 작업지시, 날짜로 작업자 배정 조회
     * @param workerId 작업자 ID
     * @param productionOrderId 작업지시 ID
     * @param assignmentDate 배정 날짜
     * @return 작업자 배정 리스트
     */
    List<WorkerAssignment> findAssignmentsByWorkerAndProductionOrderAndDate(Long workerId, Long productionOrderId, LocalDate assignmentDate);

    /**
     * 특정 작업자가 해당 날짜에 이미 배정되었는지 여부 확인
     * @param workerId 작업자 ID
     * @param date 배정 날짜
     * @return 배정 여부
     */
    boolean existsByWorkerIdAndAssignmentDate(Long workerId, LocalDate date);

    /**
     * 작업장 코드와 날짜로 작업자 배정 정보 조회
     * 날짜가 주어지지 않을 경우에도 호출이 가능하며, 이 경우 모든 작업자 배정 이력을 반환할 수 있습니다.
     * @param workcenterCode 작업장 코드
     * @param optionalDate 배정 날짜(Optional)
     * @return 작업자 배정 리스트
     */
    List<WorkerAssignment> getWorkerAssignments(String workcenterCode, Optional<LocalDate> optionalDate);

    /**
     * 작업장 코드와 날짜로 작업자 배정 정보 조회
     * @param workcenterCode 작업장 코드
     * @param date 배정 날짜
     * @return 작업자 배정 리스트
     */
    List<WorkerAssignment> findWorkerAssignmentsByWorkcenterCodeAndDate(String workcenterCode, LocalDate date);

//    /**
//     * 작업장 코드로 오늘의 작업자 조회
//     * @param code 작업장 코드
//     * @return 오늘의 작업자 리스트
//     */
//    List<WorkerAssignmentDTO> findTodayWorkers(String code);

    /**
     * 특정 작업자와 날짜로 작업자 배정 조회
     * @param workerId 작업자 ID
     * @param assignmentDate 배정 날짜
     * @return 작업자 배정(Optional로 감싸서 반환)
     */


}
