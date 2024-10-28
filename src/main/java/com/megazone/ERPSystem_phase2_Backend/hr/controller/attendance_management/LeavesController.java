package com.megazone.ERPSystem_phase2_Backend.hr.controller.attendance_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.Leaves;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.LeavesAllShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.LeavesCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.attendance_management.dto.LeavesShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.attendance_management.Leaves.LeavesRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.attendance_management.Leaves.LeavesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr/leaves")
public class LeavesController {
    private final LeavesService leavesService;

    // 휴가 등록
    @PostMapping("/createLeaves")
    public ResponseEntity<LeavesShowDTO> createLeave(@RequestBody LeavesCreateDTO dto) {
        LeavesShowDTO leave = leavesService.createLeave(dto);
        return new ResponseEntity<>(leave, HttpStatus.CREATED);
    }

    // 휴가 리스트 조회
    @PostMapping("/list")
    public ResponseEntity<List<LeavesAllShowDTO>> getLeaveList() {
        List<LeavesAllShowDTO> leavesList = leavesService.getLeavesList();
        return new ResponseEntity<>(leavesList, HttpStatus.OK);
    }
}
