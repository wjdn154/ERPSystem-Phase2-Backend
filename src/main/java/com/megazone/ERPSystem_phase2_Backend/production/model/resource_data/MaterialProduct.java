package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 자재와 품목 다대다 중간 엔티티
 * */
@Entity(name = "material_product")
@Table(name = "material_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialData materialData;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
