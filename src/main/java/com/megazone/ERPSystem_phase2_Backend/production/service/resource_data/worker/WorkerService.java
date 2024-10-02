package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.worker;


import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerDetailShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.EquipmentDataUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.dto.ListEquipmentDataDTO;

import java.util.List;
import java.util.Optional;

public interface WorkerService {

    //작업자 교육이수여부 등록
    Optional<WorkerDetailShowDTO> saveWorker(Long companyId, WorkerDetailShowDTO dto);

    //작업자 교육이수여부 수정
    Optional<WorkerDetailShowDTO> updateWorker(Long id, WorkerDetailShowDTO dto);

    //생산관리 작업자 리스트 조회
    List<ListWorkerDTO> findAllWorker();

    //생산관리 작업자 상세 조회
    Optional<WorkerDetailShowDTO> findWorkerById(Long id);

    //작업자 근태내역 조회
    ListWorkerAttendanceDTO findAllWorkerById(Long id);

}
