package com.hackthon.service.serviceInter;

import com.hackthon.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(String message, String type);

    NotificationDto Notification(String message, String type, Long id);// utilisateur récupéré via token

    List<NotificationDto> viewAllNotifications();

    List<NotificationDto> viewUnreadNotifications();

    void markAsRead(Long notificationId);

    void markAllAsRead();

    String deleteNotification(Long notificationId);

}
