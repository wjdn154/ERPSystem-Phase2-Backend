package com.megazone.ERPSystem_phase2_Backend.hr.model.requirement_management.jobposting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// 지원자 엔티티

@Data
@Entity(name="jobposting_applicant")
@Table(name="jobposting_applicant")
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "applicant")
    private List<Application> application; // 지원서 목록

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false, unique = true)
    private String email; // 이메일wld

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Column
    private String resume; // 이력서 URL 또는 파일 경로
}
