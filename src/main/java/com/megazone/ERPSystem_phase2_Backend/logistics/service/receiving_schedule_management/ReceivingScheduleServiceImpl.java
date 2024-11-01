package com.megazone.ERPSystem_phase2_Backend.logistics.service.receiving_schedule_management;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.ReceivingSchedule;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderProcessRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderProcessRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingScheduleResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.enums.ReceivingStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_location.WarehouseLocation;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order.ReceivingOrderDetailRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.receiving_processing_management.ReceivingScheduleRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.warehouse_location_management.WarehouseLocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReceivingScheduleServiceImpl implements ReceivingScheduleService {

    private final ReceivingOrderDetailRepository receivingOrderDetailRepository;
    private final WarehouseLocationRepository warehouseLocationRepository;
    private final ReceivingScheduleRepository receivingScheduleRepository;
    private final InventoryRepository inventoryRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;


    @Override
    public List<ReceivingScheduleResponseDTO> getReceivingSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
// 날짜 범위와 상태가 WAITING인 모든 ReceivingSchedule 엔티티 조회
        List<ReceivingSchedule> schedules = receivingScheduleRepository.findAllByReceivingDateBetweenAndStatus(startDate, endDate, ReceivingStatus.WAITING);

        // 각 엔티티를 DTO로 변환
        return schedules.stream().map(schedule ->
                new ReceivingScheduleResponseDTO(
                        schedule.getId(),
                        schedule.getReceivingOrderDetail().getId(),
                        schedule.getWarehouseLocation().getId(),
                        schedule.getWarehouseLocation().getLocationName(),
                        schedule.getProductName(),
                        schedule.getPendingInventoryNumber(),
                        schedule.getPendingQuantity(),
                        schedule.getReceivingDate().toString() + " - " + schedule.getReceivingDateNumber()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public void registerReceivingSchedules(ReceivingOrderProcessRequestListDTO requestListDTO) {
        // ReceivingOrderDetail 조회
        ReceivingOrderDetail receivingOrderDetail = receivingOrderDetailRepository.findById(requestListDTO.getReceivingOrderDetailId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiving order detail ID"));

        // 요청 지시 수량의 총합 계산
        int totalRequestedQuantity = requestListDTO.getRequests().stream()
                .mapToInt(ReceivingOrderProcessRequestDTO::getPendingQuantity)
                .sum();

        // 총 지시 수량이 원래 수량을 넘는 경우 예외 처리
        if (totalRequestedQuantity > receivingOrderDetail.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available quantity.");
        }

        // 남은 미입고 수량 계산 및 업데이트
        int remainingUnreceivedQuantity = receivingOrderDetail.getQuantity() - totalRequestedQuantity;
        receivingOrderDetail = ReceivingOrderDetail.builder()
                .id(receivingOrderDetail.getId())
                .receivingOrder(receivingOrderDetail.getReceivingOrder())
                .product(receivingOrderDetail.getProduct())
                .quantity(receivingOrderDetail.getQuantity())
                .unreceivedQuantity(remainingUnreceivedQuantity)
                .build();
        receivingOrderDetailRepository.save(receivingOrderDetail);

        // 각 요청 항목을 ReceivingSchedule 엔티티로 변환하여 저장
        LocalDate receivingDate = LocalDate.parse(requestListDTO.getReceivingDate());
        for (ReceivingOrderProcessRequestDTO request : requestListDTO.getRequests()) {
            // 해당 날짜의 최대 receivingDateNumber 조회
            Long maxDateNumber = receivingScheduleRepository.findMaxReceivingDateNumberByDate(receivingDate);
            Long nextDateNumber = (maxDateNumber != null) ? maxDateNumber + 1 : 1;

            // WarehouseLocation 조회
            WarehouseLocation warehouseLocation = warehouseLocationRepository.findById(request.getWarehouseLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid warehouse location ID"));

            ReceivingSchedule receivingSchedule = ReceivingSchedule.builder()
                    .receivingOrderDetail(receivingOrderDetail)
                    .warehouseLocation(warehouseLocation)
                    .productName(request.getProductName())
                    .pendingInventoryNumber(request.getPendingInventoryNumber())
                    .pendingQuantity(request.getPendingQuantity())
                    .receivingDate(receivingDate)
                    .receivingDateNumber(nextDateNumber)
                    .status(ReceivingStatus.WAITING)
                    .build();

            receivingScheduleRepository.save(receivingSchedule);

            // 알림 생성
            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription("새로운 입고 예정 스케줄이 등록되었습니다.")
                    .activityType(ActivityType.LOGISTICS)
                    .activityTime(LocalDateTime.now())
                    .build());

            notificationService.createAndSendNotification(
                    ModuleType.LOGISTICS,
                    PermissionType.ADMIN,
                    "새로운 입고 스케줄이 등록되었습니다.",
                    NotificationType.RECEIVING_COMPLETE
            );
        }
    }

    @Override
    public void processReceivingSchedule(Long scheduleId) {
        // ReceivingSchedule 조회
        ReceivingSchedule receivingSchedule = receivingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiving schedule ID"));

        // Inventory 생성
        Inventory newInventory = Inventory.builder()
                .warehouse(receivingSchedule.getWarehouseLocation().getWarehouse())
                .warehouseLocation(receivingSchedule.getWarehouseLocation())
                .product(receivingSchedule.getReceivingOrderDetail().getProduct())
                .inventoryNumber(receivingSchedule.getPendingInventoryNumber()) // 재고 번호 설정
                .standard(receivingSchedule.getProductName())  // 규격이 제품 이름으로 설정됨 (변경 가능)
                .quantity(receivingSchedule.getPendingQuantity().longValue())  // 수량 설정
                .build();

        // Inventory 저장
        inventoryRepository.save(newInventory);
    }

}