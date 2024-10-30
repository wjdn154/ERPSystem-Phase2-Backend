package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "equipment_data_image")
@Table(name = "equipment_data_image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDataImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_picture", nullable = false)
    private String imagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreateAt() {
        this.createdAt = LocalDateTime.now();


    }
}
