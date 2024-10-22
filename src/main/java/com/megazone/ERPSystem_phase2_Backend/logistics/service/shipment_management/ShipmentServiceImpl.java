package com.megazone.ERPSystem_phase2_Backend.logistics.service.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ContactInfoRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.Shipment;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.ShipmentProduct;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentProductListResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseAddressRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management.ShipmentProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipment_management.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService{

    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;
    private final ShipmentProductRepository shipmentProductRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseAddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ContactInfoRepository contactInfoRepository;



    @Override
    public List<ShipmentResponseListDTO> findShipmentListByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Shipment> shipments = shipmentRepository.findShipmentListByDateRange(startDate, endDate);

        return shipments.stream()
                .map(shipment -> {
                    String shipmentNumber = shipment.getShipmentDate() + " - " + shipment.getShipmentNumber();

                    String firstProductName = generateFirstProductName(shipment);

                    Long totalQuantity = shipment.getProducts().stream()
                            .mapToLong(ShipmentProduct::getQuantity)
                            .sum();

                    return new ShipmentResponseListDTO(
                            shipment.getId(),
                            shipmentNumber,
                            shipment.getWarehouse().getName(),
                            firstProductName,
                            totalQuantity,
                            shipment.getClient().getPrintClientName()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ShipmentResponseDTO getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .map(ShipmentResponseDTO::mapToDto)
                .orElse(null);
    }


    @Override
    public ShipmentResponseDTO createShipment(ShipmentRequestDTO requestDTO) {
        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다."));
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));
        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("고객 정보를 찾을 수 없습니다."));
        Long maxShipmentNumber = shipmentRepository.findMaxShipmentNumberByDate(requestDTO.getShipmentDate());

        Long newShipmentNumber = maxShipmentNumber + 1;

        Shipment shipment = Shipment.builder()
                .warehouse(warehouse)
                .employee(employee)
                .client(client)
                .clientName(client.getPrintClientName())
                .contactInfo(requestDTO.getContactInfo())
                .address(requestDTO.getAddress())
                .shipmentDate(requestDTO.getShipmentDate())
                .shipmentNumber(newShipmentNumber)
                .comment(requestDTO.getComment())
                .build();

        List<ShipmentProduct> shipmentProducts = requestDTO.getProducts().stream()
                .map(productDTO -> {
                    Product product = productRepository.findById(productDTO.getProductId())
                            .orElseThrow(() -> new RuntimeException("제품 정보를 찾을 수 없습니다."));
                    return ShipmentProduct.builder()
                            .shipment(shipment) // 생성 시 Shipment 설정
                            .product(product)
                            .productCode(productDTO.getProductCode())
                            .productName(productDTO.getProductName())
                            .standard(productDTO.getStandard())
                            .unit(productDTO.getUnit())
                            .quantity(productDTO.getQuantity())
                            .comment(productDTO.getComment())
                            .build();
                }).collect(Collectors.toList());

        shipment.getProducts().addAll(shipmentProducts);

        Shipment savedShipment = shipmentRepository.save(shipment);

        return ShipmentResponseDTO.mapToDto(savedShipment);
    }

    @Override
    public ShipmentResponseDTO updateShipment(Long id, ShipmentRequestDTO requestDTO) {
        Shipment existingShipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("출하 전표를 찾을 수 없습니다."));

        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다."));
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다."));
        Client client = clientRepository.findById(requestDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("고객 정보를 찾을 수 없습니다."));

        LocalDate originalShipmentDate = existingShipment.getShipmentDate();
        LocalDate newShipmentDate = requestDTO.getShipmentDate();
        Long newShipmentNumber = existingShipment.getShipmentNumber();

        if (!originalShipmentDate.equals(newShipmentDate)) {
            Long maxShipmentNumber = shipmentRepository.findMaxShipmentNumberByDate(newShipmentDate);
            newShipmentNumber = maxShipmentNumber + 1;
        }

        Shipment updatedShipment = existingShipment.builder()
                .id(existingShipment.getId())
                .warehouse(warehouse)
                .employee(employee)
                .client(client)
                .clientName(client.getPrintClientName())
                .contactInfo(requestDTO.getContactInfo())
                .address(requestDTO.getAddress())
                .shipmentDate(newShipmentDate)
                .shipmentNumber(newShipmentNumber)
                .comment(requestDTO.getComment())
                .build();

        final Shipment finalShipment = updatedShipment;
        List<ShipmentProduct> newShipmentProducts = requestDTO.getProducts().stream()
                .map(productDTO -> {
                    Product product = productRepository.findById(productDTO.getProductId())
                            .orElseThrow(() -> new RuntimeException("제품 정보를 찾을 수 없습니다."));
                    return ShipmentProduct.builder()
                            .shipment(finalShipment)
                            .product(product)
                            .productCode(productDTO.getProductCode())
                            .productName(productDTO.getProductName())
                            .standard(productDTO.getStandard())
                            .unit(productDTO.getUnit())
                            .quantity(productDTO.getQuantity())
                            .comment(productDTO.getComment())
                            .build();
                }).collect(Collectors.toList());

        updatedShipment.getProducts().clear();
        updatedShipment.getProducts().addAll(newShipmentProducts);

        Shipment savedShipment = shipmentRepository.save(updatedShipment);

        return ShipmentResponseDTO.mapToDto(savedShipment);
    }

    @Override
    public void deleteShipment(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("삭제할 출하 전표를 찾을 수 없습니다."));

        shipmentRepository.deleteById(shipmentId);
    }

    @Override
    public List<ShipmentProductListResponseDTO> findShipmentItemsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ShipmentProduct> shipmentProducts = shipmentProductRepository.findShipmentItemsByDateRange(startDate, endDate);

        return shipmentProducts.stream()
                .map(ShipmentProductListResponseDTO::mapToDto)
                .collect(Collectors.toList());
    }


    private String generateFirstProductName(Shipment shipment) {
        List<ShipmentProduct> products = shipment.getProducts();
        if (products.isEmpty()) {
            return "품목 없음";
        }
        String firstProductName = products.get(0).getProductName();
        int additionalCount = products.size() - 1;
        return additionalCount > 0 ? firstProductName + " 외 " + additionalCount + "건" : firstProductName;
    }
}
