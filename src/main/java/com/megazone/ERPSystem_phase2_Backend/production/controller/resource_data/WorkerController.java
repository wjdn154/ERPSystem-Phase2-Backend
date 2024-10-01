package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerAttendanceDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerDetailShowDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class WorkerController {

    private final WorkerService workerService;
    private final UsersRepository usersRepository;

    //작업자 리스트 조회
    @PostMapping("/workers")
    public ResponseEntity<List<ListWorkerDTO>> allWorkers() {

        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = user.getCompany().getId();

        //서비스에서 회사아이디에 해당하는 부서가 생산인 모든 작업자 정보를 가져옴
        List<ListWorkerDTO> result = workerService.findAllWorker(companyId);

        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //작업자 상세 정보 조회
    @PostMapping("/worker/{id}")
    public ResponseEntity<WorkerDetailShowDTO> showWorkerDetail(@PathVariable("id") Long id){

        //서비스에서 해당 작업자 상세 정보 가져옴
        Optional<WorkerDetailShowDTO> result = workerService.findWorkerById(id);

        //해당 아이디의 상세정보가 존재하지 않을 경우 404 반환
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //작업자 상세 정보 수정(교육이수 여부만 수정 가능)
    @PutMapping("/worker/updateWorker/{id}")
    public ResponseEntity<WorkerDetailShowDTO> updateWorkerDetail(@PathVariable("id") Long id, @RequestBody WorkerDetailShowDTO dto){

        //서비스에서 해당 작업자 상세 정보를 수정함
        Optional<WorkerDetailShowDTO> result = workerService.updateWorker(id,dto);

        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //해당 작업자의 모든 작업배치 목록 조회
    @PostMapping("/worker/attendance/{id}")
    public ResponseEntity<ListWorkerAttendanceDTO> allWorkerAttendance(@PathVariable("id") Long id){

        //서비스에서 해당 작업자의 근태,작업배치 목록을 가져옴
        ListWorkerAttendanceDTO result = workerService.findAllWorkerById(id);

        return ResponseEntity.ok(result);
    }


}
