package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.WorkerAssignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentSummaryDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerAssignmentServiceImpl implements WorkerAssignmentService {

    private final WorkerAssignmentRepository workerAssignmentRepository;
    private final ShiftTypeRepository shiftTypeRepository;
    private final WorkerRepository workerRepository;
    private final WorkcenterRepository workcenterRepository;
    private final ProductionOrderRepository productionOrderRepository;

    // 전체 작업장별 배정된 인원수 조회
    @Override
    public List<WorkerAssignmentDTO> getAllWorkcentersWorkerCount() {
        return workerAssignmentRepository.findWorkerCountByWorkcenter().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 작업장 배정된 작업자 명단 조회
    @Override
    public List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(String workcenterCode) {
        return workerAssignmentRepository.findByWorkcenterCode(workcenterCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 현재 날짜 기준 작업자 배정명단 조회
    @Override
    public WorkerAssignmentSummaryDTO getTodayWorkerAssignments(LocalDate currentDate, boolean includeShiftType, Long shiftTypeId) {
        return getWorkerAssignments(currentDate, includeShiftType, shiftTypeId);
    }


    // 특정 날짜에 동일 작업자가 이미 배정된지 확인 (중복 방지)
    @Override
    public boolean isWorkerAlreadyAssigned(Long workerId, LocalDate date) {
        return workerAssignmentRepository.existsByWorkerIdAndAssignmentDate(workerId, date);
    }

    @Override
    public WorkerAssignmentSummaryDTO getWorkerAssignmentsByDate(LocalDate date, boolean includeShiftType, Long shiftTypeId) {
        return getWorkerAssignments(date, includeShiftType, shiftTypeId);
    }


    // 오늘의 작업장별 배정인원 상세명단과 인원수 조회
    @Override
    public WorkerAssignmentSummaryDTO getTodayWorkerAssignmentsSummary(LocalDate currentDate, boolean includeShiftType, Long shiftTypeId) {
        List<WorkerAssignment> assignments;

        if (includeShiftType && shiftTypeId != null) {
            ShiftType shift = shiftTypeRepository.findById(shiftTypeId)
                    .orElseThrow(() -> new EntityNotFoundException("교대유형을 찾을 수 없습니다."));
            assignments = workerAssignmentRepository.findByAssignmentDateAndShiftTypeId(currentDate, shiftTypeId);
        } else {
            assignments = workerAssignmentRepository.findByAssignmentDate(currentDate);
        }

        List<WorkerAssignmentDTO> details = assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 공장별로 작업자 수 집계
        Map<String, Long> workcenterCounts = details.stream()
                .collect(Collectors.groupingBy(WorkerAssignmentDTO::getWorkcenterCode, Collectors.counting()));

        return new WorkerAssignmentSummaryDTO(details, workcenterCounts);
    }

    @Override
    public List<WorkerAssignmentDTO> getWorkerAssignmentsByDateRange(LocalDate startOfMonth, LocalDate endOfMonth) {
        // 날짜 범위 내의 작업자 배정 목록 조회
        List<WorkerAssignment> assignments = workerAssignmentRepository.findByAssignmentDateBetween(startOfMonth, endOfMonth);
        return assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerAssignmentSummaryDTO getWorkerAssignmentsByProductionOrder(Long productionOrderId, boolean includeShiftType, Long shiftTypeId) {
        List<WorkerAssignment> assignments;

        if (includeShiftType && shiftTypeId != null) {
            assignments = workerAssignmentRepository.findByProductionOrderIdAndShiftTypeId(productionOrderId, shiftTypeId);
        } else {
            assignments = workerAssignmentRepository.findByProductionOrderId(productionOrderId);
        }

        List<WorkerAssignmentDTO> assignmentDTOs = assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // 작업장별 배정 인원 수 집계
        Map<String, Long> workcenterCounts = assignmentDTOs.stream()
                .collect(Collectors.groupingBy(
                        WorkerAssignmentDTO::getWorkcenterCode,
                        Collectors.counting()
                ));

        return WorkerAssignmentSummaryDTO.builder()
                .details(assignmentDTOs)
                .workcenterCounts(workcenterCounts)
                .build();
    }

    @Override
    public List<WorkerAssignmentDTO> getWorkerAssignmentsByWorker(Long workerId, boolean includeShiftType, Long shiftTypeId) {

        List<WorkerAssignment> assignments;

        if (includeShiftType && shiftTypeId != null) {
            // 교대유형 필터링 적용
            assignments = workerAssignmentRepository.findByWorkerIdAndShiftTypeId(workerId, shiftTypeId);
        } else {
            // 교대유형 필터링 없이 모든 배정 목록 조회
            assignments = workerAssignmentRepository.findByWorkerId(workerId);
        }

        // WorkerAssignment 엔티티를 WorkerAssignmentDTO로 변환
        return assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // 반복되는 공통작업을 처리하는 메서드
    private WorkerAssignmentSummaryDTO getWorkerAssignments(
            LocalDate date, boolean includeShiftType, Long shiftTypeId) {
        List<WorkerAssignment> assignments;

        if (includeShiftType && shiftTypeId != null) {
            assignments = workerAssignmentRepository.findByAssignmentDateAndShiftTypeId(date, shiftTypeId);
        } else {
            assignments = workerAssignmentRepository.findByAssignmentDate(date);
        }

        List<WorkerAssignmentDTO> assignmentDTOs = assignments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, Long> workcenterCounts = assignmentDTOs.stream()
                .collect(Collectors.groupingBy(WorkerAssignmentDTO::getWorkcenterCode, Collectors.counting()));

        return WorkerAssignmentSummaryDTO.builder()
                .details(assignmentDTOs)
                .workcenterCounts(workcenterCounts)
                .build();
    }

    // DTO 변환 메서드 (WorkerAssignment -> WorkerAssignmentDTO)
    private WorkerAssignmentDTO convertToDTO(WorkerAssignment assignment) {
        return WorkerAssignmentDTO.builder()
                .id(assignment.getId())
                .workerId(assignment.getWorker().getId())
                .workerName(assignment.getWorker().getEmployee().getLastName() + " " + assignment.getWorker().getEmployee().getFirstName())
                .employeeNumber(assignment.getWorker().getEmployee().getEmployeeNumber())
                .workcenterCode(assignment.getWorkcenter().getCode())
                .assignmentDate(assignment.getAssignmentDate())
                .shiftTypeId(assignment.getShiftType().getId())
                .build();
    }

    // 엔티티 변환 메서드 (WorkerAssignmentDTO -> WorkerAssignment)
    private WorkerAssignment convertToEntity(WorkerAssignmentDTO dto) {
        Worker worker = workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByCode(dto.getWorkcenterCode())
                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
        ShiftType shiftType = shiftTypeRepository.findById(dto.getShiftTypeId())
                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));

        return WorkerAssignment.builder()
                .worker(worker)
                .workcenter(workcenter)
                .shiftType(shiftType)
                .assignmentDate(dto.getAssignmentDate())
                .build();
    }
}