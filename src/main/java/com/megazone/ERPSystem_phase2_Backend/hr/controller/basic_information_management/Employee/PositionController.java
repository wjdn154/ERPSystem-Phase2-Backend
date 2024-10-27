package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PositionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Position.PositionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee.EmployeeService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Position.PositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class PositionController {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final PositionRepository positionRepository;

    @PostMapping("/positions")
    public ResponseEntity<List<PositionShowDTO>> getAllPositions(){
        List<Position> positions = positionRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(positions.stream().map(position -> new PositionShowDTO(
                position.getId(), position.getPositionCode() ,position.getPositionName())).collect(Collectors.toList()));
    }

    @PostMapping("/position/{id}")
    public ResponseEntity<PositionShowDTO> getPositionById(@PathVariable Long id){
        Position position = positionService.getPositionById(id).orElseThrow(() -> new EntityNotFoundException("해당 직위를 찾을 수 없습니다."));

        PositionShowDTO positionShowDTO = new PositionShowDTO(position.getId(), position.getPositionCode(), position.getPositionName());
        return ResponseEntity.ok(positionShowDTO);
    }
}
