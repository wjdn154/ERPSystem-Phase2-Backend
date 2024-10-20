package com.megazone.ERPSystem_phase2_Backend.hr.controller.requirement_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting.DTO.JobPostingDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.requirement_management.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PostMapping("/jobposting/all")
    public ResponseEntity<List<JobPostingDTO>> getAllJobPostings() {
        List<JobPostingDTO> jobPostings = jobPostingService.findAllJobPostings();

        if (jobPostings.isEmpty()) {
            return ResponseEntity.noContent().build(); // 공고가 없을 경우 204 반환
        }

        return ResponseEntity.ok(jobPostings);  // 공고 리스트 반환
    }
}
