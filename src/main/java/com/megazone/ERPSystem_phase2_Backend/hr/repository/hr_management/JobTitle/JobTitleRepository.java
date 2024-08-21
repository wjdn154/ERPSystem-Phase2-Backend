package com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.JobTitle;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long>, JobTitleRepositoryCustom {
    Optional<JobTitle> findById(Long id);
}
