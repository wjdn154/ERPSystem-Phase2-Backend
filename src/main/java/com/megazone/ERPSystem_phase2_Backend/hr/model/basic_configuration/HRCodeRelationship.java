package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "hr_code_relationship")
@Table(name = "hr_code_relationship")
/**
 * 급여 계정과목 코드 체계
 */
public class HRCodeRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_code_id")
    private HRCode parentCode;

    @ManyToOne
    @JoinColumn(name = "child_code_id")
    private HRCode childCode;

}
