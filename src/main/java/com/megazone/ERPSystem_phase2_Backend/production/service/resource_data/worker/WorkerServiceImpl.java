package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.worker;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.*;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.worker_assignment.WorkerAssignmentRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment.EquipmentDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker.WorkerRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.equipment.EquipmentDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {


    private final WorkerRepository workerRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkerAssignmentRepository workerAssignmentRepository;

    //작업자 상세 정보 수정(교육 이수 여부만 등록 가능)
    @Override
    public Optional<WorkerDetailShowDTO> saveWorker(Long companyId, WorkerDetailShowDTO dto) {
        return Optional.empty();
    }

    //작업자 상세 정보 수정(교육 이수 여부만 수정 가능)
    @Override
    public Optional<WorkerDetailShowDTO> updateWorker(Long id, WorkerDetailShowDTO dto) {

        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다." + id));

        //새로 들어온 dto를 기존 엔티티에 업데이트. 교육이수여부만 업데이트 나머지는 기존값 그대로.
        worker.setTrainingStatus(dto.getTrainingStatus());

        Worker updateWorker = workerRepository.save(worker);

        WorkerDetailShowDTO workerDetailUpdateDTO = workerToDTO(updateWorker);

        return Optional.of(workerDetailUpdateDTO);
    }

    //생산에 해당하는 작업자 목록 조회
    @Override
    public List<ListWorkerDTO> findAllWorker(Long companyId) {

        //해당 회사아이디에 해당하는 부서 작업자 목록 조회
        List<ListWorkerDTO> workerList = workerRepository.findAllWorkerByDepartmentAndCompanyId(companyId);
        return workerList;
    }

    //해당 작업자 상세 정보 조회
    @Override
    public Optional<WorkerDetailShowDTO> findWorkerById(Long id) {

        //아이디에 해당하는 작업자 엔티티 조회
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 조회할 수 없습니다." +id));

        //엔티티를 dto로 반환
        WorkerDetailShowDTO workerDetailShowDTO = workerToDTO(worker);

        //dto 반환
        return Optional.of(workerDetailShowDTO);
    }


    //해당 작업자의 모든 작업 배치 이력 조회
    @Override
    public ListWorkerAttendanceDTO findAllWorkerById(Long id) {

        //worker id로 worker 조회
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("작업자를 찾을 수 없습니다.") + id));

        //근태 관리 리스트 생성
        List<WorkerAttendanceDTO> attendanceList = worker.getEmployee().getAttendance().stream()
                .map(attendance -> new WorkerAttendanceDTO(
                        attendance.getAttendanceCode(),
                        attendance.getDate().toString(),
                        attendance.getCheckTime().toString(),
                        attendance.getCheckoutTime().toString(),
                        attendance.getStatus().toString()
                )).toList();

        //작업배치 관리 리스트 생성
        List<WorkerAssignmentDTO> assignmentList = worker.getWorkerAssignments().stream()
                .map(assignment -> new WorkerAssignmentDTO(
                        assignment.getWorkcenter().getCode(),
                        assignment.getWorkcenter().getName(),
                        assignment.getAssignmentDate().toString()
                        )).toList();
        //최종 dto 생성 및 반환
        return new ListWorkerAttendanceDTO(
                worker.getId(),
                worker.getEmployee().getEmployeeNumber(),
                worker.getEmployee().getFirstName(),
                worker.getEmployee().getLastName(),
                attendanceList,
                assignmentList
        );
    }

    //worker 엔티티를 WorkerDetailShowDTO 로 변환
    private WorkerDetailShowDTO workerToDTO(Worker worker) {

        WorkerDetailShowDTO workerDetailShowDTO = new WorkerDetailShowDTO();
        workerDetailShowDTO.setId(worker.getId());
        workerDetailShowDTO.setTrainingStatus(worker.getTrainingStatus());
        workerDetailShowDTO.setEmployeeNumber(worker.getEmployee().getEmployeeNumber());
        workerDetailShowDTO.setEmployeeFirstName(worker.getEmployee().getFirstName());
        workerDetailShowDTO.setEmployeeLastName(worker.getEmployee().getLastName());
        workerDetailShowDTO.setDepartmentName(worker.getEmployee().getDepartment().getDepartmentName());
        workerDetailShowDTO.setPositionName(worker.getEmployee().getPosition().getPositionName());
        workerDetailShowDTO.setJobTitleName(worker.getEmployee().getJobTitle().getTitleName());
        workerDetailShowDTO.setPhoneNumber(worker.getEmployee().getPhoneNumber());
        workerDetailShowDTO.setEmploymentStatus(worker.getEmployee().getEmploymentStatus());
        workerDetailShowDTO.setEmploymentType(worker.getEmployee().getEmploymentType());
        workerDetailShowDTO.setHireDate(worker.getEmployee().getHireDate());
        workerDetailShowDTO.setProfilePicture(worker.getEmployee().getProfilePicture());

        //작업배치가 비어있지 않다면 배치 날짜 기준으로 가장 최근의 작업배치 정보 가져오기
        if(worker.getWorkerAssignments() != null && !worker.getWorkerAssignments().isEmpty()){
            WorkerAssignment recentAssignment = worker.getWorkerAssignments().stream()
                            .max(Comparator.comparing(WorkerAssignment::getAssignmentDate))
                                    .orElseThrow(() -> new IllegalArgumentException("작업 배치 정보가 없습니다. "));
            workerDetailShowDTO.setWorkcenterCode(recentAssignment.getWorkcenter().getCode());
            workerDetailShowDTO.setWorkcenterName(recentAssignment.getWorkcenter().getName());
        }
//        List<WorkerAssignmentDTO> workerAssignmentDTOS = worker.getWorkerAssignments().stream()
//                .map(assignment -> {
//                    WorkerAssignmentDTO dto = new WorkerAssignmentDTO();
//                    dto.setId(assignment.getId());
//                    dto.setWorkcenterCode(assignment.getWorkcenter().getCode());
//                    dto.setWorkcenterName(assignment.getWorkcenter().getName());
//                    return dto;
//                })
//                .collect(Collectors.toList());

        return workerDetailShowDTO;
    }
}
