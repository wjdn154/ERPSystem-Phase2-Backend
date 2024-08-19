package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
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
    private Date offerDate; // 제안 일자

    @Column(nullable = false)
    private String offerDetails; // 제안 상세 내용

    @Column
    private String status; // 제안 상태 (e.g., "Accepted", "Rejected")
}
