package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 지원서 엔티티

@Data
@Entity(name="jobposting_application")
@Table(name="jobposting_application")
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jobposting_id", nullable = false)
    private JobPosting jobPosting; // 채용 공고

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant; // 지원자

    @Column(nullable = false)
    private LocalDate applicationDate; // 지원 일자

    @Column
    private String status; // 지원서 상태 (e.g., "Under Review", "Interview Scheduled", "Rejected")
}
