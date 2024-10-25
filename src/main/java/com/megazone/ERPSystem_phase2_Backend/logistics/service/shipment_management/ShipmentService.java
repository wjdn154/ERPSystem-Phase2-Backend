package com.megazone.ERPSystem_phase2_Backend.logistics.service.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentTotalProductsDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentService {
    ShipmentResponseDTO createShipment(ShipmentRequestDTO requestDTO);

    List<ShipmentResponseListDTO> findShipmentListByDateRange(LocalDate startDate, LocalDate endDate);

    ShipmentResponseDTO getShipmentById(Long id);

    ShipmentResponseDTO updateShipment(Long id, ShipmentRequestDTO requestDTO);

    void deleteShipment(Long shipmentId);


    ShipmentTotalProductsDTO findShipmentItemsByDateRange(LocalDate startDate, LocalDate endDate);
}
