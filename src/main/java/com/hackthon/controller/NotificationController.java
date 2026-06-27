package com.hackthon.controller;

import com.hackthon.dto.NotificationDto;
import com.hackthon.repository.NotificationRepository;
import com.hackthon.service.serviceInter.NotificationService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping(Constants.ENDPOINT_Notification)
@Tag(name = "Notification")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private final NotificationService notificationService;

    @GetMapping("/Unread/for-connectuser")
    public ResponseEntity<List<NotificationDto>> getNotificationsNonLues() {
        return ResponseEntity.ok(notificationService.viewUnreadNotifications());
    }

    @PostMapping("/markAsRead/{notifId}")
    public void marquerCommeLue(@PathVariable Long notifId) {
        notificationService.markAsRead(notifId);
    }

    @DeleteMapping("/{notifId}")
    public ResponseEntity<?> deleteNotification(Long notificationId) {
       return  ResponseEntity.ok(notificationService.deleteNotification(notificationId));
    }
    @GetMapping("/for-connectuser")
    public ResponseEntity<List<NotificationDto>>  viewAllNotifications() {
        return ResponseEntity.ok(notificationService.viewAllNotifications());
    }

    @PostMapping("/{type}")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody String message, @PathVariable String type) {
        return ResponseEntity.ok(notificationService.createNotification(message, type));
    }

}
