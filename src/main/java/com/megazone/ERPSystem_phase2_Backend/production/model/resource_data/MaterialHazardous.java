package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자재와 유해물질 다대다 중간 엔티티
 * */

@Entity(name = "material_hazardous")
@Table(name = "material_hazardous")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialHazardous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id" , nullable = false)
    private MaterialData materialData;

    @ManyToOne
    @JoinColumn(name = "hazardous_material_id", nullable = false)
    private HazardousMaterial hazardousMaterial;
}
