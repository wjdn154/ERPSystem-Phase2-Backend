package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// 채용 공고 엔티티

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // 관련 부서 참조

    @OneToMany(mappedBy = "jobPosting")
    private List<Application> application; // 지원서 목록

    @Column(nullable = false)
    private String title; // 공고 제목

    @Column(nullable = false)
    private String description; // 공고 설명

    @Column(nullable = false)
    private Date postingDate; // 공고 등록일

    @Column(nullable = false)
    private Date closingDate; // 공고 마감일
}
