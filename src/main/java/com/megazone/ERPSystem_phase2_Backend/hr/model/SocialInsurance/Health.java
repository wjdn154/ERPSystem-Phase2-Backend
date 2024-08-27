package com.megazone.ERPSystem_phase2_Backend.hr.model.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

/*
    건강보험 테이블
*/
@Data
@Entity
@Table
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
