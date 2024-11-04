package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.JobTitle;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Position;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.JobTitleShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PositionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.JobTitle.JobTitleRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee.EmployeeService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.JobTitle.JobTitleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class JobTitleController {
    private final EmployeeService employeeService;
    private final JobTitleService jobTitleService;
    private final JobTitleRepository jobTitleRepository;


    @PostMapping("/jobTitles")
    public List<JobTitleShowDTO> getAllJobTitles() {
        List<JobTitle> jobTitles = jobTitleRepository.findAll();
        return jobTitles.stream().map(jobTitle -> new JobTitleShowDTO(jobTitle.getId(),jobTitle.getJobTitleCode(), jobTitle.getJobTitleName(), jobTitle.getDescription())).collect(Collectors.toList());
    }

    @PostMapping("/jobTitle/{id}")
    public ResponseEntity<JobTitleShowDTO> getJobTitleById(@PathVariable("id") Long id){
        JobTitle jobTitle = jobTitleService.getJobTitleById(id).orElseThrow(() -> new EntityNotFoundException("해당 직책을 찾을 수 없습니다."));

        JobTitleShowDTO jobTitleShowDTO = new JobTitleShowDTO(jobTitle.getId(), jobTitle.getJobTitleCode(), jobTitle.getJobTitleName(), jobTitle.getDescription());
        return ResponseEntity.ok(jobTitleShowDTO);
    }
}
