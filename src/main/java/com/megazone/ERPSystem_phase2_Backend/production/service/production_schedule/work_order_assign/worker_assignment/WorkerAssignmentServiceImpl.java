package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order_assign.worker_assignment;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.ShiftType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.work_order_assign.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.work_order.WorkOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.shift_type.ShiftTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.work_order_assign.worker_assignment.WorkerAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerAssignmentServiceImpl implements WorkerAssignmentService {

    private final WorkerAssignmentRepository workerAssignmentRepository;
    private final ShiftTypeRepository shiftTypeRepository;
    private final WorkerRepository workerRepository;
    private final WorkcenterRepository workcenterRepository;
    private final WorkOrderRepository workOrderRepository;

    // 전체 작업장별 배정된 인원수 조회
    public List<WorkerAssignmentDTO> getAllWorkcentersWorkerCount() {
        return workerAssignmentRepository.findWorkerCountByWorkcenter().stream()
                .map(this::convertToDTO)  // convertToDTO를 WorkerAssignment에 대해 적용
                .collect(Collectors.toList());
    }

    // 특정 작업장 배정된 작업자 명단 조회
    public List<WorkerAssignmentDTO> getWorkersByWorkcenterCode(String workcenterCode) {
        return workerAssignmentRepository.findByWorkcenterCode(workcenterCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 현재 날짜 기준 작업자 배정명단 조회
    public List<WorkerAssignmentDTO> getTodayWorkerAssignments(LocalDate currentDate) {
        return workerAssignmentRepository.findByAssignmentDate(currentDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 날짜에 동일 작업자가 이미 배정된지 확인 (중복 방지)
    public boolean isWorkerAlreadyAssigned(Long workerId, LocalDate date) {
        return workerAssignmentRepository.existsByWorkerIdAndAssignmentDate(workerId, date);
    }

    // 작업자 배정 로직
    public WorkerAssignment assignWorkerToShift(Long workerId, String workcenterCode, Long shiftTypeId, LocalDate assignmentDate) {
        // 중복 배정 체크
        if (isWorkerAlreadyAssigned(workerId, assignmentDate)) {
            throw new IllegalArgumentException("해당 작업자는 이미 동일한 날짜에 배정되었습니다.");
        }

        // 작업자, 작업장, 교대근무유형을 조회하고 예외처리
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
        ShiftType shiftType = shiftTypeRepository.findById(shiftTypeId)
                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));

        // 작업자 배정 엔티티 생성 및 저장
        WorkerAssignment workerAssignment = new WorkerAssignment();
        workerAssignment.setWorker(worker);
        workerAssignment.setWorkcenter(workcenter);
        workerAssignment.setShiftType(shiftType);
        workerAssignment.setAssignmentDate(assignmentDate);

        return workerAssignment;
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
                .shift(assignment.getShiftType().getName())
                .build();
    }

    // 엔티티 변환 메서드 (WorkerAssignmentDTO -> WorkerAssignment)
    private WorkerAssignment convertToEntity(WorkerAssignmentDTO dto) {
        Worker worker = workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new EntityNotFoundException("작업자를 찾을 수 없습니다."));
        Workcenter workcenter = workcenterRepository.findByCode(dto.getWorkcenterCode())
                .orElseThrow(() -> new EntityNotFoundException("작업장을 찾을 수 없습니다."));
        ShiftType shiftType = shiftTypeRepository.findByName(dto.getShift())
                .orElseThrow(() -> new EntityNotFoundException("교대근무유형을 찾을 수 없습니다."));

        return WorkerAssignment.builder()
                .worker(worker)
                .workcenter(workcenter)
                .shiftType(shiftType)
                .assignmentDate(dto.getAssignmentDate())
                .build();
    }
}
