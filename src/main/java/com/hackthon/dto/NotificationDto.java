package com.hackthon.dto;

import com.hackthon.modele.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Builder
public class NotificationDto {
    private Long id;
    private String message;
    private String type;
    private Boolean lue = false;
    private LocalDateTime date;
    private Long utilisateurId;

    public static   NotificationDto fromEntity(Notification notification){
        return NotificationDto.builder()
                .message(notification.getMessage())
                .utilisateurId(notification.getUtilisateur().getId())
                .date(notification.getDate())
                .type(notification.getType())
                .id(notification.getId())
                .lue(notification.getLue())
                .build();
    }

    public static Notification toEntity(NotificationDto notificationDto){
        return Notification.builder()
                .message(notificationDto.getMessage())
                .date(LocalDateTime.now())
                .type(notificationDto.getType())
                .build();
    }

}
