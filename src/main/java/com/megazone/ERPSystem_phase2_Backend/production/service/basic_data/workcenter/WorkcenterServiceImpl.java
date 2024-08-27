package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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

    private WorkcenterDTO convertToDTO(Workcenter workcenter) {
        return WorkcenterDTO.builder()
                .id(workcenter.getId())
                .code(workcenter.getCode())
                .name(workcenter.getName())
                .description(workcenter.getDescription())
                .isActive(workcenter.getIsActive())
//                .factoryCode(workcenter.getFactory() != null ? workcenter.getFactory().getCode() : null)
//                .processCode(workcenter.getProcessDetails() != null ? workcenter.getProcessDetails().getCode() : null)
                .build();
    }

    @Override
    public Optional<WorkcenterDTO> deleteByCode(String code) {
        // 1. 작업장을 조회하고, 존재하지 않으면 예외 발생
        Workcenter workcenter = workcenterRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("작업장 코드: " + code + "를 찾을 수 없습니다."));

        // 2. 삭제 전 작업장을 DTO로 변환
        WorkcenterDTO deletedWorkcenterDTO = new WorkcenterDTO(
                workcenter.getId(),
                workcenter.getCode(),
                workcenter.getWorkcenterType(),
                workcenter.getName(),
                workcenter.getDescription(),
                workcenter.getIsActive(),
                null, // factoryCode 매핑 필요 시 추가
                null, // processCode 매핑 필요 시 추가
                null, // equipmentList 매핑 필요 시 추가
                null  // workerAssignments 매핑 필요 시 추가
        );

        // 3. 작업장 삭제
        workcenterRepository.delete(workcenter);

        // 4. 삭제된 작업장의 정보를 반환
        return Optional.of(deletedWorkcenterDTO);
    }


    @Override
    public List<WorkcenterDTO> findAll() {
        List<Workcenter> workcenters = workcenterRepository.findAll();
        return workcenters.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<WorkcenterDTO> findByNameContaining(String name) {
        List<Workcenter> workcenters = workcenterRepository.findByNameContaining(name);
        return workcenters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkcenterDTO> findByName(String name) {
        return workcenterRepository.findByName(name)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<WorkcenterDTO> findByCode(String code) {
        return workcenterRepository.findByCode(code)
                .map(this::convertToDTO);
    }


    @Override
    public Optional<WorkcenterDTO> findById(Long id) {
        return workcenterRepository.findById(id)
                .map(this::convertToDTO);
    }

    /**
     * 저장 시 중복코드 검증
     *
     * @param workcenterDTO 저장할 작업장
     * @return 저장된 Workcenter 객체
     */
    @Override
    public Workcenter save(WorkcenterDTO workcenterDTO) {
        try {
            // 1. 작업장 코드 중복 확인
            if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
                throw new RuntimeException("이미 존재하는 작업장 코드입니다: " + workcenterDTO.getCode());
            }

            // 2. WorkcenterDTO를 Workcenter 엔티티로 변환
            Workcenter workcenter = convertToEntity(workcenterDTO);

            // 3. Workcenter 엔티티 저장
            return workcenterRepository.save(workcenter);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("저장 중 오류가 발생했습니다: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Workcenter 저장 중 예상치 못한 오류가 발생했습니다: " + e.getMessage());
        }
    }


    // WorkcenterDTO -> Workcenter 메서드
    private Workcenter convertToEntity(WorkcenterDTO workcenterDTO) {
        return Workcenter.builder()
                .code(workcenterDTO.getCode())
                .name(workcenterDTO.getName())
                .workcenterType(workcenterDTO.getWorkcenterType())
                .description(workcenterDTO.getDescription())
                .isActive(workcenterDTO.getIsActive())
//                .processDetails(
//                        workcenterDTO.getProcessCode() != null ?
//                                processDetailsRepository.findByCode(workcenterDTO.getProcessCode().getCode())
//                                        .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode().getCode() + "에 해당하는 공정을 찾을 수 없습니다."))
//                                : null
//                ) // ProcessDetails 설정
//                .factory(
//                        workcenterDTO.getFactoryCode() != null ?
//                                warehouseRepository.findByCode(workcenterDTO.getFactoryCode().getCode())
//                                        .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode().getCode() + "에 해당하는 공장을 찾을 수 없습니다."))
//                                : null
//                ) // Warehouse 설정
//                .equipmentList(
//                        workcenterDTO.getEquipmentList() != null ?
//                                workcenterDTO.getEquipmentList().stream()
//                                        .map(this::convertEquipmentDataToEntity)
//                                        .collect(Collectors.toList())
//                                : new ArrayList<>()
//                ) // EquipmentData 목록 설정
//                .workerAssignments(
//                        workcenterDTO.getWorkerAssignments() != null ?
//                                workcenterDTO.getWorkerAssignments().stream()
//                                        .map(this::convertWorkerAssignmentToEntity)
//                                        .collect(Collectors.toList())
//                                : new ArrayList<>()
//                ) // WorkerAssignment 목록 설정
                .build();
    }

//    // EquipmentDataDTO -> EquipmentData 엔티티 변환 메서드
//    private EquipmentData convertEquipmentDataToEntity(EquipmentDataDTO dto) {
//    }
//
//    // WorkerAssignmentDTO -> WorkerAssignment 엔티티 변환 메서드
//    private WorkerAssignment convertWorkerAssignmentToEntity(WorkerAssignmentDTO dto) {
//        // 변환 로직 작성
//    }


//    private Workcenter convertToEntity(WorkcenterDTO workcenterDTO) {
//        Workcenter workcenter = new Workcenter();
//        workcenter.setCode(workcenterDTO.getCode());
//        workcenter.setName(workcenterDTO.getName());
//        workcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
//        workcenter.setDescription(workcenterDTO.getDescription());
//        workcenter.setIsActive(workcenterDTO.getIsActive());
//
//        // processCode를 통해 ProcessDetails를 설정
//        try {
//            if (workcenterDTO.getProcessCode() != null) {
//                ProcessDetails processDetails = processDetailsRepository.findByCode(workcenterDTO.getProcessCode().getCode())
//                        .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode().getCode() + "에 해당하는 공정을 찾을 수 없습니다."));
//                workcenter.setProcessDetails(processDetails);
//            } else {
//                throw new RuntimeException("ProcessCode is null");
//            }
//        } catch (Exception e) {
//            System.out.println("ProcessDetails 설정 중 오류 발생: " + e.getMessage());
//            workcenter.setProcessDetails(null); // 또는 기본값 설정
//        }
//
//        // factoryCode를 통해 Warehouse를 설정
//        try {
//            if (workcenterDTO.getFactoryCode() != null) {
//                Warehouse factory = warehouseRepository.findByCode(workcenterDTO.getFactoryCode().getCode())
//                        .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode().getCode() + "에 해당하는 공장을 찾을 수 없습니다."));
//                workcenter.setFactory(factory);
//            } else {
//                throw new RuntimeException("FactoryCode is null");
//            }
//        } catch (Exception e) {
//            System.out.println("Warehouse 설정 중 오류 발생: " + e.getMessage());
//            workcenter.setFactory(null); // 또는 기본값 설정
//        }
//
//        return workcenter;
//    }

    /**
     * 삭제 시 사용 중인 작업장 확인
     *
     * @param ids 삭제할 Workcenter ID
     */
    @Override
    public List<Workcenter> deleteByIds(List<Long> ids) {
        try {
            // 삭제 전 작업장들을 미리 조회하여 리스트에 저장
            List<Workcenter> workcentersToDelete = workcenterRepository.findAllById(ids);

            // 사용 중인 작업장이 있는지 확인
            boolean hasActiveWorkcenter = workcentersToDelete.stream()
                    .anyMatch(Workcenter::getIsActive);

            if (hasActiveWorkcenter) {
                throw new RuntimeException("사용 중인 작업장은 삭제할 수 없습니다.");
            }

            // 삭제
            workcenterRepository.deleteAllById(ids);

            // 삭제된 작업장 리스트 반환
            return workcentersToDelete;

        } catch (Exception e) {
            throw new RuntimeException("작업장 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public Optional<WorkcenterDTO> updateByCode(String code, WorkcenterDTO workcenterDTO) {
        return workcenterRepository.findByCode(code).map(existingWorkcenter -> {
            existingWorkcenter.setCode(workcenterDTO.getCode());
            existingWorkcenter.setName(workcenterDTO.getName());
            existingWorkcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
            existingWorkcenter.setDescription(workcenterDTO.getDescription());
            existingWorkcenter.setIsActive(workcenterDTO.getIsActive());

            // 작업장 업데이트 후 저장
            Workcenter updatedWorkcenter = workcenterRepository.save(existingWorkcenter);

            // 수정된 작업장을 DTO로 변환하여 반환
            WorkcenterDTO updatedWorkcenterDTO = new WorkcenterDTO(
                    updatedWorkcenter.getId(),
                    updatedWorkcenter.getCode(),
                    updatedWorkcenter.getWorkcenterType(),
                    updatedWorkcenter.getName(),
                    updatedWorkcenter.getDescription(),
                    updatedWorkcenter.getIsActive(),
                    null, // factoryCode 매핑 필요 시 추가
                    null, // processCode 매핑 필요 시 추가
                    null, // equipmentList 매핑 필요 시 추가
                    null  // workerAssignments 매핑 필요 시 추가
            );

            return Optional.of(updatedWorkcenterDTO);
        }).orElse(Optional.empty());
    }
}



//    @Override
//    public Optional<WorkcenterDTO> updateByCode(String code, WorkcenterDTO workcenterDTO) {
//        try {
//            // 1. 작업장 코드를 사용하여 기존 작업장을 조회
//            Workcenter existingWorkcenter = workcenterRepository.findByCode(code)
//                    .orElseThrow(() -> new RuntimeException("작업장 코드: " + code + "를 찾을 수 없습니다."));
//
//            // 2. 작업장 코드가 변경되었을 경우 중복 검사를 수행
//            if (!existingWorkcenter.getCode().equals(workcenterDTO.getCode())) {
//                if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
//                    throw new RuntimeException("코드 " + workcenterDTO.getCode() + "는 이미 존재합니다.");
//                }
//                existingWorkcenter.setCode(workcenterDTO.getCode());
//            }
//
//            // 3. 나머지 필드 업데이트
//            existingWorkcenter.setName(workcenterDTO.getName());
//            existingWorkcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
//            existingWorkcenter.setDescription(workcenterDTO.getDescription());
//            existingWorkcenter.setIsActive(workcenterDTO.getIsActive());
//
//            // 4. 작업장 업데이트 후 저장
//            Workcenter updatedWorkcenter = workcenterRepository.save(existingWorkcenter);
//
//            // 5. 업데이트된 엔티티를 DTO로 변환하여 반환
//            WorkcenterDTO updatedWorkcenterDTO = WorkcenterDTO.builder()
//                    .id(updatedWorkcenter.getId())
//                    .code(updatedWorkcenter.getCode())
//                    .workcenterType(updatedWorkcenter.getWorkcenterType())
//                    .name(updatedWorkcenter.getName())
//                    .description(updatedWorkcenter.getDescription())
//                    .isActive(updatedWorkcenter.getIsActive())
//                    // 필요한 다른 필드들도 변환하여 설정
//                    .build();
//
//            return Optional.of(updatedWorkcenterDTO);
//
//        } catch (Exception e) {
//            throw new RuntimeException("작업장 업데이트 중 오류가 발생했습니다: " + e.getMessage());
//        }
//    }
//}

//    @Override
//    public Optional<Workcenter> updateWorkcenter(String code, WorkcenterDTO workcenterDTO) {
//        try {
//            // 1. 작업장 코드를 사용하여 기존 작업장을 조회
//            Workcenter existingWorkcenter = workcenterRepository.findByCode(code)
//                    .orElseThrow(() -> new RuntimeException("작업장 코드: " + code + "를 찾을 수 없습니다."));
//
//            // 2. 작업장 코드가 변경되었을 경우 중복 검사를 수행
//            if (!existingWorkcenter.getCode().equals(workcenterDTO.getCode())) {
//                if (workcenterRepository.findByCode(workcenterDTO.getCode()).isPresent()) {
//                    throw new RuntimeException("코드 " + workcenterDTO.getCode() + "는 이미 존재합니다.");
//                }
//                existingWorkcenter.setCode(workcenterDTO.getCode());
//            }
//
//            // 3. 나머지 필드 업데이트
//            existingWorkcenter.setName(workcenterDTO.getName());
//            existingWorkcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
//            existingWorkcenter.setDescription(workcenterDTO.getDescription());
//            existingWorkcenter.setIsActive(workcenterDTO.getIsActive());
//
////            // 4. 공장 변경 처리
////            try {
////                if (workcenterDTO.getFactoryCode() != null) {
////                    // 공장코드 포함 작업장 리스트 조회
////                    List<Workcenter> workcenters = workcenterRepository.findByFactoryCodeContaining(workcenterDTO.getFactoryCode().getCode());
////
////                    // 작업장이 없으면 예외를 던짐
////                    Workcenter selectedWorkcenter = workcenters.stream()
////                            .findFirst()
////                            .orElseThrow(() -> new RuntimeException("공장 코드: " + workcenterDTO.getFactoryCode() + "에 해당하는 작업장을 찾을 수 없습니다."));
////
////                    // 첫 번째 작업장의 Factory(Warehouse)를 설정
////                    existingWorkcenter.setFactory(selectedWorkcenter.getFactory());
////                }
////            } catch (Exception e) {
////                System.out.println("Factory 설정 중 오류 발생: " + e.getMessage());
////                existingWorkcenter.setFactory(null); // 또는 기본값 설정
////            }
////
////            // 생산공정 변경 처리
////            try {
////                if (workcenterDTO.getProcessCode() != null) {
////                    // 공정코드가 포함된 작업장 리스트를 조회
////                    List<Workcenter> workcenters = workcenterRepository.findByCodeContaining(workcenterDTO.getProcessCode().getCode());
////
////                    // 작업장이 없을 경우 예외를 던짐
////                    Workcenter selectedWorkcenter = workcenters.stream()
////                            .findFirst()
////                            .orElseThrow(() -> new RuntimeException("생산 공정 코드: " + workcenterDTO.getProcessCode() + "에 해당하는 작업장을 찾을 수 없습니다."));
////
////                    // 첫 번째 작업장의 ProcessDetails를 설정
////                    existingWorkcenter.setProcessDetails(selectedWorkcenter.getProcessDetails());
////                }
////            } catch (Exception e) {
////                System.out.println("ProcessDetails 설정 중 오류 발생: " + e.getMessage());
////                existingWorkcenter.setProcessDetails(null); // 또는 기본값 설정
////            }
////
////            // 5. 작업장 업데이트 후 저장
////            return workcenterRepository.save(existingWorkcenter);
//
//        } catch (Exception e) {
//            throw new RuntimeException("작업장 업데이트 중 오류가 발생했습니다: " + e.getMessage());
//        }
//        return Optional.empty();
//    }
//}