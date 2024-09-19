package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTransferProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_transfer_id", nullable = false)
    private WarehouseTransfer warehouseTransfer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "comment")
    private String comment;

}
