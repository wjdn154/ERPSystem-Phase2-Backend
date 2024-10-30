package com.megazone.ERPSystem_phase2_Backend.Integrated.repository.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.UserNotification;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    @Query("SELECT u.notification.id FROM user_notification u WHERE u.userId = :userId")
    List<Long> findNotificationIdsByUserId(Long userId);

    List<UserNotification> findByUserIdAndReadStatusFalse(Long userId);
    List<UserNotification> findByUserIdOrderByCreateAtDesc(Long userId);

    Optional<UserNotification> findByUserIdAndNotificationId(Long userId, Long notificationId);


}