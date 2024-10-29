package com.megazone.ERPSystem_phase2_Backend.Integrated.repository.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.UserNotification;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findByUsersAndReadStatusFalse(Users users);
}