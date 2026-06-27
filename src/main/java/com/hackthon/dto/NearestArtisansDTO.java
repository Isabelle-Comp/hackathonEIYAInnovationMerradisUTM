package com.hackthon.dto;

import com.hackthon.modele.ServiceOffert;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NearestArtisansDTO {
    String userGoogleLink;
    String categorie;
}
