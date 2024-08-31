package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 채용 제안 엔티티
//  보통 후보자에게 제공되는 조건(예: 직책, 급여, 근무지, 근무 조건 등)이 포함

@Data
@Entity(name="jobposting_offer")
@Table(name="jobposting_offer")
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application; // 지원서

    @Column(nullable = false)
    private LocalDate offerDate; // 제안 일자

    @Column(nullable = false)
    private String offerDetails; // 제안 상세 내용

    @Column
    private String status; // 제안 상태 (e.g., "Accepted", "Rejected")
}
