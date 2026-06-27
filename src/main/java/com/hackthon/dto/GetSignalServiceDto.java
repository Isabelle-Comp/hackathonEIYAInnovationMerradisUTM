package com.hackthon.dto;

import com.hackthon.modele.SignalementsServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class GetSignalServiceDto {
    private Long id;
    private String motif;
    private LocalDateTime date;
    private Long serviceId;
    private Long IdAuteurSignalement;

    public static GetSignalServiceDto fromEntity(SignalementsServices signalementsServices){
        return GetSignalServiceDto.builder()
                .id(signalementsServices.getId())
                .motif(signalementsServices.getMotif())
                .IdAuteurSignalement(signalementsServices.getAuteur_du_signalement().getId())
                .date(LocalDateTime.now())
                .serviceId(signalementsServices.getService().getId())
                .build();
    }
    public SignalementsServices ToEntity(GetSignalServiceDto signalServiceDto){
        return SignalementsServices.builder()
                .build();
    }
}
