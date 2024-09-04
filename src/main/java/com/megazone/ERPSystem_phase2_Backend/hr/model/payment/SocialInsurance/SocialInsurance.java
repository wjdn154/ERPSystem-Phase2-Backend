package com.megazone.ERPSystem_phase2_Backend.hr.model.payment.SocialInsurance;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class SocialInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}
