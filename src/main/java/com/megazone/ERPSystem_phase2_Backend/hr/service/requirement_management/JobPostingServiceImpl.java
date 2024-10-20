package com.megazone.ERPSystem_phase2_Backend.hr.service.requirement_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting.DTO.JobPostingDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting.JobPosting;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.requirement_management.JobPosting.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    // 모든 채용 공고를 조회하여 DTO로 변환
    public List<JobPostingDTO> findAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();

        return jobPostings.stream()
                .map(jobPosting -> new JobPostingDTO(
                        jobPosting.getId(),
                        jobPosting.getDepartment().getDepartmentName(),
                        jobPosting.getTitle(),
                        jobPosting.getDescription(),
                        jobPosting.getPostingDate(),
                        jobPosting.getClosingDate()
                ))
                .collect(Collectors.toList());
    }

}
