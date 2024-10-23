package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.dto.WarehouseResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.WorkerAssignmentDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails.ProcessDetailsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkcenterServiceImpl implements WorkcenterService {

    private final WorkcenterRepository workcenterRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProcessDetailsRepository processDetailsRepository;
    private final EquipmentDataRepository equipmentDataRepository;
    private final WorkerAssignmentRepository workerAssignmentRepository;

    // DTO로 변환하는 메서드
    private WorkcenterDTO convertToDTO(Workcenter workcenter) {

        // 공장 타입이 FACTORY 또는 OUTSOURCING_FACTORY인 경우에만 데이터 설정
        boolean isValidWarehouse = workcenter.getFactory() != null &&
                (workcenter.getFactory().getWarehouseType() == WarehouseType.FACTORY ||
                        workcenter.getFactory().getWarehouseType() == WarehouseType.OUTSOURCING_FACTORY);

        return WorkcenterDTO.builder()
                .id(workcenter.getId())
                .code(workcenter.getCode())
                .workcenterType(workcenter.getWorkcenterType())
                .name(workcenter.getName())
                .description(workcenter.getDescription())
                .isActive(workcenter.getIsActive())

                // 조건에 따라 factoryCode 설정
                .factoryCode(isValidWarehouse ? workcenter.getFactory().getCode() : null)
                .factoryName(isValidWarehouse ? workcenter.getFactory().getName() : null)
//                .warehouseType(isValidWarehouse ? workcenter.getFactory().getWarehouseType() : null) // 확인용: 나중에 삭제
                // 생산공정
                .processCode(workcenter.getProcessDetails() != null ? workcenter.getProcessDetails().getCode() : null)
                // 설비
//                .equipmentIds(workcenter.getEquipmentList().stream().map(EquipmentData::getId).collect(Collectors.toList()))
                // 작업자 배치
//                .workerAssignmentIds(workcenter.getWorkerAssignments().stream().map(WorkerAssignment::getId).collect(Collectors.toList()))
                .build();
    }

    // Entity로 변환하는 메서드
    private Workcenter convertToEntity(WorkcenterDTO workcenterDTO) {

        return Workcenter.builder()
                .code(workcenterDTO.getCode())
                .name(workcenterDTO.getName())
                .workcenterType(workcenterDTO.getWorkcenterType())
                .description(workcenterDTO.getDescription())
                .isActive(workcenterDTO.getIsActive())

                .factory(workcenterDTO.getFactoryCode() != null ?
                        warehouseRepository.findByCode(workcenterDTO.getFactoryCode())
                                .orElseThrow(() -> new RuntimeException("해당 공장코드를 찾을 수 없습니다 : " + workcenterDTO.getFactoryCode())) : null)

                .processDetails(workcenterDTO.getProcessCode() != null ?
                        processDetailsRepository.findByCode(workcenterDTO.getProcessCode())
                                .orElseThrow(() -> new RuntimeException("해당 생산공정코드를 찾을 수 없습니다: " + workcenterDTO.getProcessCode())) : null)

//                .equipmentList(Optional.ofNullable(workcenterDTO.getEquipmentIds())
//                        .orElseGet(ArrayList::new).stream()
//                        .map(id -> equipmentDataRepository.findById(id)
//                                .orElseThrow(() -> new RuntimeException("해당 설비ID를 찾을 수 없습니다: " + id)))
//                        .collect(Collectors.toList()))

//                .workerAssignments(Optional.ofNullable(workcenterDTO.getWorkerAssignmentIds())
//                        .orElseGet(ArrayList::new).stream()
//                        .map(id -> workerAssignmentRepository.findById(id)
//                                .orElseThrow(() -> new RuntimeException("작업자배정이력ID를 찾을 수 없습니다: " + id)))
//                        .collect(Collectors.toList()))

                .build();
    }

    @Override
    public Optional<WorkcenterDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<WorkcenterDTO> deleteByCode(String code) {
        Workcenter workcenter = workcenterRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("해당 작업장코드를 찾을 수 없습니다: " + code));

        if (workcenter.getIsActive()) {
            throw new IllegalArgumentException("사용 중인 작업장은 삭제할 수 없습니다.");
        }

        workcenterRepository.delete(workcenter);
        return Optional.of(convertToDTO(workcenter));
    }

    @Override
    public Optional<WorkcenterDTO> updateByCode(String code, WorkcenterDTO workcenterDTO) {

        if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent() && !code.equals(workcenterDTO.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 작업장 코드입니다: " + workcenterDTO.getCode());
        }

        return workcenterRepository.findByCode(code).map(existingWorkcenter -> {
            existingWorkcenter.setCode(workcenterDTO.getCode());
            existingWorkcenter.setName(workcenterDTO.getName());
            existingWorkcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
            existingWorkcenter.setDescription(workcenterDTO.getDescription());
            existingWorkcenter.setIsActive(workcenterDTO.getIsActive());

            // 업데이트된 작업장 저장
            Workcenter updatedWorkcenter = workcenterRepository.save(existingWorkcenter);

            return convertToDTO(updatedWorkcenter);
        });
    }

    @Override
    public Workcenter save(WorkcenterDTO workcenterDTO) {

        // 작업장 코드 중복 확인
        if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 코드입니다: " + workcenterDTO.getCode());
        }

        Workcenter workcenter = convertToEntity(workcenterDTO);
        return workcenterRepository.save(workcenter);
    }

    @Override
    public List<WorkcenterDTO> findAll() {
        LocalDate today = LocalDate.now();
        List<Workcenter> workcenters = workcenterRepository.findAllWithDetails();

        return workcenters.stream().map(workcenter -> {
            WorkcenterDTO workcenterDTO = convertToDTO(workcenter);

            // 오늘의 작업자 수 가져오기
//            int todayWorkerCount = getTodayWorkerCount(workcenter.getCode(), today);
//            workcenterDTO.setTodayWorkerCount((long) todayWorkerCount); // 오늘 작업자 수 설정

            // 오늘의 작업자 명단 가져오기
            List<WorkerAssignmentDTO> todayWorkers = findTodayWorkers(workcenter.getCode());

            // 오늘의 작업자 리스트 설정 (작업자 명단이 없으면 '배정없음' 설정)
//            workcenterDTO.setTodayWorkers(todayWorkers);

            // 설치된 설비 수 가져오기
            List<Long> equipmentIds = equipmentDataRepository.findByWorkcenterId(workcenter.getId()).stream()
                    .map(EquipmentData::getId)
                    .collect(Collectors.toList());
            workcenterDTO.setEquipmentIds(equipmentIds); // 설비 ID 설정

            // 소속 공장명 가져오기 (공장이 있으면 이름 설정, 없으면 null)
            String factoryCode = workcenter.getFactory() != null ? workcenter.getFactory().getCode() : null;
            workcenterDTO.setFactoryCode(factoryCode);

            // 생산 공정명 가져오기 (공정이 있으면 이름 설정, 없으면 null)
            String processCode = workcenter.getProcessDetails() != null ? workcenter.getProcessDetails().getCode() : null;
            workcenterDTO.setProcessCode(processCode);

            return workcenterDTO;
        }).collect(Collectors.toList());
    }


    public int getTodayWorkerCount(String workcenterCode, LocalDate currentDate) {
        List<WorkerAssignment> todayAssignments = workerAssignmentRepository.getWorkerAssignments(workcenterCode, Optional.of(currentDate));
        return todayAssignments.size(); // 작업자 수 반환
    }

    @Override
    public List<WorkerAssignmentDTO> findTodayWorkers(String workcenterCode) {
        LocalDate today = LocalDate.now();

        // repository에서 오늘의 작업자 배정 이력을 가져옴
        List<WorkerAssignment> assignments = workerAssignmentRepository
                .findWorkerAssignmentsByWorkcenterCodeAndDate(workcenterCode, today);

        // DTO로 변환 (null 체크 추가)
        return assignments.stream()
                .map(assignment -> WorkerAssignmentDTO.builder()
                        .workerId(assignment.getWorker().getId())  // 작업자 ID
                        .workerName(assignment.getWorker().getEmployee() != null ?
                                assignment.getWorker().getEmployee().getLastName() + " " + assignment.getWorker().getEmployee().getFirstName() : "Unknown")  // 작업자 이름
                        .employeeNumber(assignment.getWorker().getEmployee() != null ?
                                assignment.getWorker().getEmployee().getEmployeeNumber() : "해당없음")  // 사원 번호
                        .workcenterCode(assignment.getWorkcenter().getCode())  // 작업장 코드
                        .assignmentDate(assignment.getAssignmentDate())  // 배정 날짜
                        .shiftTypeId(assignment.getShiftType() != null ? assignment.getShiftType().getId() : null)  // 교대조 ID
                        .productionOrderId(assignment.getProductionOrder() != null ? assignment.getProductionOrder().getId() : null)  // 작업지시 ID (null 체크)
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public Optional<WorkcenterDTO> findByCode(String code) {
        return workcenterRepository.findByCode(code).map(workcenter -> {
            WorkcenterDTO workcenterDTO = convertToDTO(workcenter);

            LocalDate today = LocalDate.now();
//            List<WorkerAssignmentDTO> todayWorkers = findTodayWorkers(code);
//            workcenterDTO.setTodayWorkerCount(todayWorkers != null ? (long) todayWorkers.size() : 0L);
//            workcenterDTO.setTodayWorkers(todayWorkers);

            return workcenterDTO;
        });
    }


//    @Override
//    public Optional<WorkcenterDTO> findById(Long id) {
//        return workcenterRepository.findById(id).map(workcenter -> {
//            WorkcenterDTO workcenterDTO = convertToDTO(workcenter);
//
//            LocalDate today = LocalDate.now();
//            List<String> todayWorkers = getTodayWorkers(workcenter.getId(), today);
//            workcenterDTO.setTodayWorkers(todayWorkers);
//
//            return workcenterDTO;
//        });
//    }

//    @Override
//    public List<WorkcenterDTO> findByNameContaining(String name) {
//        return workcenterRepository.findByNameContaining(name).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<WarehouseResponseDTO> findAllFactories() {
        return warehouseRepository.findAll().stream()
                .filter(warehouse -> warehouse.getWarehouseType() == WarehouseType.FACTORY
                        || warehouse.getWarehouseType() == WarehouseType.OUTSOURCING_FACTORY)
                .map(this::convertWarehouseToDTO)
                .collect(Collectors.toList());
    }

    private WarehouseResponseDTO convertWarehouseToDTO(Warehouse warehouse) {
        return new WarehouseResponseDTO(
                warehouse.getId(),
                warehouse.getCode(),
                warehouse.getName(),
                warehouse.getWarehouseType(),
                warehouse.getProcessDetail() != null ? warehouse.getProcessDetail().getName() : null,
                warehouse.getIsActive()
        );
    }

    @Override
    public List<EquipmentDataDTO> findEquipmentByWorkcenterCode(String workcenterCode) {
        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
                .orElseThrow(() -> new EntityNotFoundException("작업장 코드를 찾을 수 없습니다: " + workcenterCode));

        // 설비 목록이 null일 경우 빈 리스트로 처리
        List<EquipmentData> equipmentList = Optional.ofNullable(equipmentDataRepository.findByWorkcenterId(workcenter.getId())).orElse(Collections.emptyList());

        if (equipmentList.isEmpty()) {
            throw new EntityNotFoundException("해당 작업장에 설치된 설비가 없습니다: " + workcenterCode);
        }

        // 설비 목록을 DTO로 변환하여 반환
        return equipmentList.stream()
                .map(this::convertEquipmentToDTO)
                .collect(Collectors.toList());
    }


    private EquipmentDataDTO convertEquipmentToDTO(EquipmentData equipmentData) {
        return new EquipmentDataDTO(
                equipmentData.getEquipmentNum(),          // 설비 번호
                equipmentData.getEquipmentName(),         // 설비 이름
                equipmentData.getEquipmentType(),         // 설비 유형
                equipmentData.getManufacturer(),          // 제조사
                equipmentData.getModelName(),             // 모델명
                equipmentData.getPurchaseDate(),          // 구매 날짜
                equipmentData.getInstallDate(),           // 설치 날짜
                equipmentData.getOperationStatus(),       // 가동 상태
                equipmentData.getCost(),                  // 비용
                equipmentData.getWorkcenter() != null ? equipmentData.getWorkcenter().getCode() : null, // 작업장 코드
                equipmentData.getFactory() != null ? equipmentData.getFactory().getCode() : null,       // 공장 코드
                equipmentData.getEquipmentImg(),           // 설비 이미지
                equipmentData.getCompany() != null ? equipmentData.getCompany().getId() : null
        );
    }

//    @Override
//    public List<WorkerAssignmentDTO> findWorkerAssignmentsByWorkcenterCode(String workcenterCode) {
//        Workcenter workcenter = workcenterRepository.findByCode(workcenterCode)
//                .orElseThrow(() -> new EntityNotFoundException("작업장 코드를 찾을 수 없습니다: " + workcenterCode));
//
//        return workcenter.getWorkerAssignments().stream()
//                .map(this::convertWorkerAssignmentToDTO)
//                .collect(Collectors.toList());
//    }

    private WorkerAssignmentDTO convertWorkerAssignmentToDTO(WorkerAssignment workerAssignment) {
        return WorkerAssignmentDTO.builder()
                .id(workerAssignment.getId())  // 배정 이력 ID
                .workerId(workerAssignment.getWorker().getId())  // 작업자 ID
                .workcenterCode(workerAssignment.getWorkcenter().getCode()) // 작업장 CODE
                .assignmentDate(workerAssignment.getAssignmentDate())  // 배정 날짜
                .shiftTypeId(workerAssignment.getShiftType().getId())  // 교대조 정보
                .build();
    }
}
