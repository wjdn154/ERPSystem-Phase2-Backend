package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 인터뷰 엔티티

@Data
@Entity(name="jobposting_interview")
@Table(name="jobposting_interview")
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application; // 지원서

    @Column(nullable = false)
    private LocalDate interviewDate; // 인터뷰 일자

    @Column
    private String location; // 인터뷰 장소

    @Column
    private String interviewer; // 인터뷰어 이름

    @Column
    private String notes; // 인터뷰 노트
}
