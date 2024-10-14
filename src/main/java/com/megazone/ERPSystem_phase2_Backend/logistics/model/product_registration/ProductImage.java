package com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 품목 이미지 테이블
 * 품목에 업로드할 이미지가 있는 테이블 - 품목 등록 시 사용
 */
@Entity(name = "product_image")
@Table(name = "product_image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreateAt(){
        this.createdAt = LocalDateTime.now();
    }

}
